package HDBanktraining.CitadApi.services.ClientServices.impl;

import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.ClientResponse;
import HDBanktraining.CitadApi.entities.CitadEntity;
import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.mappers.ClientMappers;
import HDBanktraining.CitadApi.repository.ClientRepo.ClientRepo;
import HDBanktraining.CitadApi.services.ClientServices.ClientService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;



@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepo clientRepo;
    private final ClientMappers clientMappers;

    private static final Logger logger = Logger.getLogger(ClientServiceImpl.class);
    public ClientServiceImpl(ClientRepo clientRepo, ClientMappers clientMappers) {
        this.clientRepo = clientRepo;
        this.clientMappers = clientMappers;
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

    @Override
    public Mono<Boolean> checkBalance(String client_id, Double amount) {
        ClientEntity clientEntity = clientRepo.findById(client_id);
        if (clientEntity == null) {
            logger.info("Client not found");
            return Mono.just(false);
        }
        if (clientEntity.getWallet() < amount) {
            logger.info("Balance not enough");
            return Mono.just(false);
        }
        return Mono.just(true);
    }

    @Override
    public Mono<ClientEntity> getClientById(String clientId) {
        ClientEntity clientEntity = clientRepo.findById(clientId);
        if (clientEntity == null) {
            logger.info("Client not found");
            return Mono.empty();
        }
        return Mono.just(clientEntity);
    }

    @Override
    public Mono<BaseReponse<ClientResponse>> getClientByNumber(String number) {
        if (number == null || number.isEmpty() || !number.matches("\\d+")) {
            return Mono.just(new BaseReponse<>(
                    ResponseEnum.BAD_REQUEST.getResponseCode(),
                    ResponseEnum.BAD_REQUEST.getMessage(),
                    null));
        }

        return Mono.justOrEmpty(clientRepo.findByNumber(number))
                .map(clientMappers::entityToResponse)
                .map(clientResponse -> new BaseReponse<>(ResponseEnum.SUCCESS.getResponseCode(),
                        ResponseEnum.SUCCESS.getMessage(),
                        clientResponse))
                .switchIfEmpty(Mono.just(new BaseReponse<>(ResponseEnum.DATA_NOT_FOUND.getResponseCode(),
                        ResponseEnum.DATA_NOT_FOUND.getMessage(),
                        null)));
    }

    @Override
    public Mono<Void> updateBalance(ClientEntity client, double amount, boolean isDeposit) {
        if (isDeposit) {
            client.setWallet(client.getWallet() + amount);
        } else {
            client.setWallet(client.getWallet() - amount);
        }
        clientRepo.save(client);
        return Mono.empty();
    }
}
