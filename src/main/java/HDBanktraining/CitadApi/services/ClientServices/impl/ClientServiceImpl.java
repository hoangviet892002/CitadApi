package HDBanktraining.CitadApi.services.ClientServices.impl;

import HDBanktraining.CitadApi.entities.CitadEntity;
import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.repository.ClientRepo.ClientRepo;
import HDBanktraining.CitadApi.services.ClientServices.ClientService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;



@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepo clientRepo;

    private static final Logger logger = Logger.getLogger(ClientServiceImpl.class);
    public ClientServiceImpl(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public Mono<Void> insertClient(CitadEntity citadEntity) {
        ClientEntity clientEntity = ClientEntity.DefaultEntites(citadEntity);
        ClientEntity existClient = clientRepo.findByEmail(clientEntity.getEmail());
        if (existClient != null) {
            logger.info("Client already exist");
            return Mono.empty();
        }
        clientRepo.save(clientEntity);
        logger.info("Insert client success");
        return Mono.empty();
    }
}
