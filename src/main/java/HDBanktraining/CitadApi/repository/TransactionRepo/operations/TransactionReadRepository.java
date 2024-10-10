package HDBanktraining.CitadApi.repository.TransactionRepo.operations;

import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionReadRepository {

    TransactionEntity findById(String transactionId);

    Page<TransactionEntity> findAllBySenderId(String accountNumber, Pageable pageable);

    @Query("SELECT t FROM TransactionEntity t WHERE t.sender = :client OR t.id IN (SELECT tr.transaction.id FROM TransferTransactionEntity tr WHERE tr.receiver = :client)")
    Page<TransactionEntity> findAllByClient(@Param("client") ClientEntity client, Pageable pageable);
}






