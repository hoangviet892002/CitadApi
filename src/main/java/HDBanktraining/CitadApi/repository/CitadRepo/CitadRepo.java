package HDBanktraining.CitadApi.repository.CitadRepo;

import HDBanktraining.CitadApi.entities.CitadEntity;
import HDBanktraining.CitadApi.repository.CitadRepo.operations.CitadCreateRepository;
import HDBanktraining.CitadApi.repository.CitadRepo.operations.CitadDeleteRepository;
import HDBanktraining.CitadApi.repository.CitadRepo.operations.CitadReadRepository;
import HDBanktraining.CitadApi.repository.CitadRepo.operations.CitadUpdateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitadRepo extends
        JpaRepository<CitadEntity, Long>,
        CitadCreateRepository,
        CitadReadRepository,
        CitadUpdateRepository,
        CitadDeleteRepository {
}
