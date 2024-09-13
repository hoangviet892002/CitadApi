package HDBanktraining.CitadApi.services.TranferTransactionService.operation;

import HDBanktraining.CitadApi.entities.TransferTransactionEntity;
import reactor.core.publisher.Mono;

public interface TranferTransactionQuery {
    Mono<TransferTransactionEntity> getTransactionById(String id);
}
