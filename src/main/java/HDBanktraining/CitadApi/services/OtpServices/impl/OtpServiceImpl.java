package HDBanktraining.CitadApi.services.OtpServices.impl;

import HDBanktraining.CitadApi.dtos.response.OtpResponse;
import HDBanktraining.CitadApi.entities.OtpEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.repository.OtpRepo.OtpRepo;
import HDBanktraining.CitadApi.services.OtpServices.OtpService;
import HDBanktraining.CitadApi.utils.OtpUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class OtpServiceImpl implements OtpService {
    private final OtpRepo otpRepo;


    public OtpServiceImpl(OtpRepo otpRepo) {
        this.otpRepo = otpRepo;
    }


    @Override
    public Mono<OtpResponse> insertOtp(TransactionEntity transactionEntity) {
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setTransaction(transactionEntity);
        otpEntity.setOtp(OtpUtil.generateOTP());
        OtpEntity newOtp = otpRepo.save(otpEntity);
        OtpResponse otpResponse = new OtpResponse(newOtp.getOtp());
        return Mono.just(otpResponse);
    }
}
