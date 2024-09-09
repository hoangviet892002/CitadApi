package HDBanktraining.CitadApi.repository.TransactionRepo;

import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.repository.TransactionRepo.operations.TransactionCreateRepository;
import HDBanktraining.CitadApi.repository.TransactionRepo.operations.TransactionDeleteRepository;
import HDBanktraining.CitadApi.repository.TransactionRepo.operations.TransactionReadRepository;
import HDBanktraining.CitadApi.repository.TransactionRepo.operations.TransactionUpdateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends
        JpaRepository<TransactionEntity, Long>,
        TransactionCreateRepository,
        TransactionDeleteRepository,
        TransactionReadRepository,
        TransactionUpdateRepository
{
}
