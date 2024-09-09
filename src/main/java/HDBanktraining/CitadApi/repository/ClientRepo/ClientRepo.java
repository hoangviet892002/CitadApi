package HDBanktraining.CitadApi.repository.ClientRepo;

import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.repository.ClientRepo.operations.ClientCreateRepository;
import HDBanktraining.CitadApi.repository.ClientRepo.operations.ClientDeleteRepository;
import HDBanktraining.CitadApi.repository.ClientRepo.operations.ClientReadRepository;
import HDBanktraining.CitadApi.repository.ClientRepo.operations.ClientUpdateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends
        JpaRepository<ClientEntity, Long>,
        ClientCreateRepository,
        ClientDeleteRepository,
        ClientReadRepository,
        ClientUpdateRepository {
}
