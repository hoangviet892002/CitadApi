package HDBanktraining.CitadApi.repository.TransactionRepo.operations;

import HDBanktraining.CitadApi.entities.TransactionEntity;

public interface TransactionReadRepository {
    TransactionEntity findById(String transactionId);
}
