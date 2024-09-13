package HDBanktraining.CitadApi.repository.TransferTransactionRepo.operations;

import HDBanktraining.CitadApi.entities.TransferTransactionEntity;

public interface TransferTransactionReadRepository {
    TransferTransactionEntity findByTransactionId(String id);
}
