package HDBanktraining.CitadApi.repository.CitadRepo.operations;

import HDBanktraining.CitadApi.entities.CitadEntity;

import java.util.Optional;

public interface CitadReadRepository {
    CitadEntity findById (String id);

    Optional<CitadEntity> findByCode(String code);
}
