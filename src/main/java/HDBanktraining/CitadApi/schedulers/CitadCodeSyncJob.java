package HDBanktraining.CitadApi.schedulers;

import HDBanktraining.CitadApi.services.CitadServices.impl.CitadServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CitadCodeSyncJob {

    private final CitadServiceImpl citadService;

    private static final Logger logger = Logger.getLogger(CitadCodeSyncJob.class);

    public CitadCodeSyncJob(CitadServiceImpl citadService) {
        this.citadService = citadService;
    }

    // 1:00 AM everyday
    @Scheduled(cron = "0 0 1 * * *")
    public void syncCitadCodeFromExcel() throws IOException {
        citadService.checkAndSaveCitadData().subscribe(
                unused -> logger.info("Citad data sync completed successfully."),
                error -> logger.error("Error during Citad data sync", error)
        );
    }

}
