package HDBanktraining.CitadApi.services.TranferTransactionService.operation;

import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.entities.TransferTransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TranferTransactionQuery {
    Mono<TransferTransactionEntity> getTransactionById(String id);

    Page<TransferTransactionEntity> getTransactionByAccountNumber(String accountNumber, Pageable pageable);

    // get transfer transaction mapping by transactions
    List<TransferTransactionEntity> getTransferTransactionByTransactions(List<TransactionEntity> transactionIds);
}
