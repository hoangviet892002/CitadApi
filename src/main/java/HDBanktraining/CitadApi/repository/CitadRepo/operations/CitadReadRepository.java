package HDBanktraining.CitadApi.repository.CitadRepo.operations;

import HDBanktraining.CitadApi.entities.CitadEntity;

import java.util.Optional;

public interface CitadReadRepository {
    CitadEntity findById (String id);

    CitadEntity findByCode(String code);
}
