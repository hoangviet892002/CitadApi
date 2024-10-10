package HDBanktraining.CitadApi.services.OtpServices.impl;

import HDBanktraining.CitadApi.configs.ScheduleConfig;
import HDBanktraining.CitadApi.dtos.response.OtpResponse;
import HDBanktraining.CitadApi.entities.OtpEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.quartz.job.OTPCleanupJob;
import HDBanktraining.CitadApi.repository.OtpRepo.OtpRepo;
import HDBanktraining.CitadApi.services.OtpServices.OtpService;
import HDBanktraining.CitadApi.services.TransactionServices.TransactionService;
import HDBanktraining.CitadApi.shared.enums.TransactionStatusEnum;
import HDBanktraining.CitadApi.utils.OtpUtil;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service

public class OtpServiceImpl implements OtpService {
    private final OtpRepo otpRepo;
    private final TransactionService transactionService;
    private final Scheduler scheduler = ScheduleConfig.getScheduler();

    private static final Logger logger = Logger.getLogger(OtpServiceImpl.class);

    public OtpServiceImpl(OtpRepo otpRepo,@Lazy TransactionService transactionService) {
        this.otpRepo = otpRepo;
        this.transactionService = transactionService;
    }

    @Override
    public Mono<OtpResponse> insertOtp(TransactionEntity transactionEntity) {
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setTransaction(transactionEntity);
        otpEntity.setOtp(OtpUtil.generateOTP());
        OtpEntity newOtp = otpRepo.save(otpEntity);
        OtpResponse otpResponse = new OtpResponse(newOtp.getOtp());
        try {
            scheduleOTPCleanup(newOtp);
        } catch (Exception e) {
            logger.error("Error when schedule OTP cleanup", e);
            cleanupOtp(newOtp.getId());
        }
        return Mono.just(otpResponse);
    }

    private void scheduleOTPCleanup(OtpEntity otpEntity) throws Exception {
        logger.info("Schedule OTP cleanup job");
        JobKey jobKey = new JobKey("otpCleanupJob" + otpEntity.getId(), "otpCleanupGroup");
        TriggerKey triggerKey = new TriggerKey("otpCleanupTrigger" + otpEntity.getId(), "otpCleanupGroup");
        // Check if job already exists, delete it
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
        JobDetail jobDetail = JobBuilder.newJob(OTPCleanupJob.class)
                .withIdentity(jobKey)
                .usingJobData("otpId", otpEntity.getId())
                .build();
        // OTP expired time: 5 minutes
        final long OTP_EXPIRED_TIME = 5  * 1000;
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .startAt(new java.util.Date(System.currentTimeMillis() + OTP_EXPIRED_TIME))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        logger.info("Schedule OTP cleanup job success");
    }

    @Override
    public void cleanupOtp(String otpId) {
        OtpEntity otpEntity = otpRepo.findOtpById(otpId);
        if (otpEntity == null) {
            logger.info("OTP not found");
            return;
        }
        TransactionEntity transactionEntity = otpEntity.getTransaction();
        transactionEntity.setStatus(TransactionStatusEnum.FAILED.getValue());
        otpEntity.setActive(false);
        Mono.zip( transactionService.updateTransaction(transactionEntity),
                Mono.fromCallable(() -> otpRepo.save(otpEntity))
        ).block();
        logger.info("OTP cleanup success");
    }

    @Override
    public Mono<OtpEntity> findByTransactionId(TransactionEntity transactionId) {
        return Mono.just(otpRepo.findOtpByTransactionId(transactionId.getId()));
    }

    @Override
    public Mono<OtpResponse> resetOtp(TransactionEntity transactionEntity) {
        OtpEntity otpEntity = otpRepo.findOtpByTransactionId(transactionEntity.getId());
        if (otpEntity == null) {
            return insertOtp(transactionEntity);
        }
        otpEntity.setOtp(OtpUtil.generateOTP());
        otpEntity.setActive(true);
        OtpEntity newOtp = otpRepo.save(otpEntity);
        OtpResponse otpResponse = new OtpResponse(newOtp.getOtp());
        try {
            scheduleOTPCleanup(newOtp);
        } catch (Exception e) {
            logger.error("Error when schedule OTP cleanup", e);
            cleanupOtp(newOtp.getId());
        }
        return Mono.just(otpResponse);
    }

    @Override
    public Mono<Boolean> checkOtp(String transactionId, String otp) throws Exception {
        OtpEntity otpEntity = otpRepo.findOtpByTransactionId(transactionId);
        if (otpEntity == null) {
            return Mono.just(false);
        }
        if (!otpEntity.isActive()) {
            return Mono.just(false);
        }
        if (!otpEntity.getOtp().equals(otp)) {
            return Mono.just(false);
        }
        cancleScheduleOTPCleanup(otpEntity);
        otpEntity.setActive(false);
        otpRepo.save(otpEntity);
        return Mono.just(true);
    }

    private void cancleScheduleOTPCleanup(OtpEntity otpEntity) throws Exception {
        logger.info("Cancel schedule OTP cleanup job");
        JobKey jobKey = new JobKey("otpCleanupJob" + otpEntity.getId(), "otpCleanupGroup");
        TriggerKey triggerKey = new TriggerKey("otpCleanupTrigger" + otpEntity.getId(), "otpCleanupGroup");
        // Check if job already exists, delete it
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
        logger.info("Cancel schedule OTP cleanup job success");
    }
}
