package HDBanktraining.CitadApi.configs;


import lombok.Getter;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import org.springframework.context.annotation.Configuration;


@Configuration
public class ScheduleConfig {
    private static final Logger logger = Logger.getLogger(ScheduleConfig.class);
    @Getter
    private static Scheduler scheduler;



    static {
        try {
            // Khởi tạo và bắt đầu Scheduler
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            logger.info("Scheduler started");
        } catch (SchedulerException e) {
            logger.error("Error when start scheduler", e);
        }
    }







}
