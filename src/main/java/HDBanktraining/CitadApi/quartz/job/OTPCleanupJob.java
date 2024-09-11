package HDBanktraining.CitadApi.quartz.job;

import HDBanktraining.CitadApi.services.OtpServices.OtpService;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class OTPCleanupJob implements Job, ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private OtpService otpService;

    private static final Logger logger = Logger.getLogger(OTPCleanupJob.class);

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext  = context;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if (otpService == null) {
            otpService = applicationContext.getBean(OtpService.class);
        }
        logger.info("Execute OTP cleanup job");
        String otpId = jobExecutionContext.getJobDetail().getJobDataMap().getString("otpId");
        otpService.cleanupOtp(otpId);
    }
}
