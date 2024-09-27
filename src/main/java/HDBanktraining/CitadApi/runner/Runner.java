package HDBanktraining.CitadApi.runner;

import HDBanktraining.CitadApi.entities.CitadEntity;
import HDBanktraining.CitadApi.services.CitadServices.CitadService;
import HDBanktraining.CitadApi.services.ClientServices.ClientService;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class Runner implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(Runner.class);

    private final CitadService citadService;
    private final ClientService clientService;

    public Runner(CitadService citadService, ClientService clientService) {
        this.citadService = citadService;
        this.clientService = clientService;
    }

    @Override
    public void run(String... args) {
//        citadService.queryCitad("79321001")
//                .doOnNext(citadEntity -> {
//                    if (citadEntity != null) {
//                        logger.info("Citad already exists");
//                        clientService.insertClient(citadEntity)
//                                .doOnSuccess(v -> logger.info("Insert client success"))
//                                .doOnError(e -> logger.error("Error inserting client", e))
//                                .subscribe();
//                    } else {
//                        CitadEntity newCitadEntity = new CitadEntity(
//                                "79321001",
//                                "Ngân hàng TMCP Phát triển Thành phố Hồ Chí Minh (Ho Chi Minh Development Bank)",
//                                "Tất cả (All branches)"
//                        );
//                        try {
//                            citadService.insertCitadData(newCitadEntity)
//                                    .doOnSuccess(v -> logger.info("Citad inserted successfully"))
//                                    .doOnError(e -> logger.error("Error inserting Citad", e))
//                                    .subscribe();
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                        logger.info("Citad not found");
//                    }
//                })
//                .doOnError(e -> logger.error("Error querying Citad", e))
//                .subscribe();
    }
}
