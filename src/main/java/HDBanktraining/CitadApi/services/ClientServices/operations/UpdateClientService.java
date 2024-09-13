package HDBanktraining.CitadApi.services.ClientServices.operations;

import HDBanktraining.CitadApi.entities.ClientEntity;
import reactor.core.publisher.Mono;

public interface UpdateClientService {
    Mono<Void> updateBalance(ClientEntity client, double amount, boolean isDeposit);
}
