package HDBanktraining.CitadApi.repository.TransferNapas.operations;

import HDBanktraining.CitadApi.entities.TransferNapasEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransferNapasReadRepo {

    TransferNapasEntity findByTransactionId(String id);


}
