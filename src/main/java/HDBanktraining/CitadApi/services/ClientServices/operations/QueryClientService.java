package HDBanktraining.CitadApi.services.ClientServices.operations;

import HDBanktraining.CitadApi.dtos.response.ClientResponse;
import HDBanktraining.CitadApi.entities.ClientEntity;
import reactor.core.publisher.Mono;

public interface QueryClientService {
    Mono<Boolean> checkBalance(String client_id, Double amount);

    Mono<ClientEntity> getClientById(String clientId);

    Mono<ClientResponse> getClientByNumber(String number);
}
