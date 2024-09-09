package HDBanktraining.CitadApi.runner;

import HDBanktraining.CitadApi.entities.CitadEntity;
import HDBanktraining.CitadApi.services.CitadServices.CitadService;
import HDBanktraining.CitadApi.services.ClientServices.ClientService;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


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
    public void run(String... args) throws Exception {
        CitadEntity citadEntity = citadService.queryCitad("79321001").block();
        if (citadEntity != null) {
            logger.info("Citad already exist");
            clientService.insertClient(citadEntity).block();
            logger.info("Insert client success");
        }
    }
}
