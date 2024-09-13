package HDBanktraining.CitadApi.services.TranferTransactionService.operation;

import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import reactor.core.publisher.Mono;

public interface TranferTransactionInsert {
    Mono<Void> insert(TransactionEntity transaction, ClientEntity receiver);
}
