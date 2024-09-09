package HDBanktraining.CitadApi.repository.ClientRepo.operations;

import HDBanktraining.CitadApi.entities.ClientEntity;

public interface ClientReadRepository {
    ClientEntity findByEmail(String email);
}
