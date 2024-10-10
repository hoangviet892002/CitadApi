package HDBanktraining.CitadApi.repository.TransferTransactionRepo.operations;

import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.entities.TransferTransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransferTransactionReadRepository {
    TransferTransactionEntity findByTransactionId(String id);

    Page<TransferTransactionEntity> findAllByReceiverId(String accountNumber, Pageable pageable);


    //findAllByTransactionIn
    List<TransferTransactionEntity> findAllByTransactionIn(List<TransactionEntity> transactionIds);
}
