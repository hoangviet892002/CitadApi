package HDBanktraining.CitadApi.repository.CitadRepo.operations;

import HDBanktraining.CitadApi.entities.CitadEntity;

import java.util.List;

public interface CitadCreateRepository {
    boolean existsByCode(String code);


}
