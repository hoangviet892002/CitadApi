package HDBanktraining.CitadApi.schedulers;

import HDBanktraining.CitadApi.services.CitadServices.impl.CitadServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CitadCodeSyncJob {

    private final CitadServiceImpl citadService;

    public CitadCodeSyncJob(CitadServiceImpl citadService) {
        this.citadService = citadService;
    }

    // 1:00 AM everyday
    @Scheduled(cron = "0 0 1 * * *")
    public void syncCitadCodeFromExcel() throws IOException {
        citadService.checkAndSaveCitadData();
    }
}
