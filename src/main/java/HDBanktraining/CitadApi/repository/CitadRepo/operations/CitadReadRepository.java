package HDBanktraining.CitadApi.repository.CitadRepo.operations;

import HDBanktraining.CitadApi.entities.CitadEntity;

public interface CitadReadRepository {
    CitadEntity findById (String id);

    CitadEntity findByCode (String code);

}
