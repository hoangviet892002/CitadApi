package HDBanktraining.CitadApi.services.TransactionServices.operations;

import HDBanktraining.CitadApi.entities.TransactionEntity;
import reactor.core.publisher.Mono;

public interface QueryTransactionService {
    Mono<TransactionEntity> queryTransaction(String transactionId);
}
