package HDBanktraining.CitadApi.services.TransactionServices.operations;

import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.response.TransactionResponse;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;



public interface QueryTransactionService {
    Mono<TransactionEntity> queryTransaction(String transactionId);

    Mono<BaseList<TransactionResponse>> queryTransactions(String accountID, Pageable pageable);
}
