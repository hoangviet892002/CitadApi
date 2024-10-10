package HDBanktraining.CitadApi.repository.TransferNapas;

import HDBanktraining.CitadApi.entities.TransferNapasEntity;
import HDBanktraining.CitadApi.repository.TransferNapas.operations.TransferNapasCreateRepo;
import HDBanktraining.CitadApi.repository.TransferNapas.operations.TransferNapasDeleteRepo;
import HDBanktraining.CitadApi.repository.TransferNapas.operations.TransferNapasReadRepo;
import HDBanktraining.CitadApi.repository.TransferNapas.operations.TransferNapasUpdateRepo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferNapasRepo extends
        JpaRepository<TransferNapasEntity, Long> ,
        TransferNapasCreateRepo,
        TransferNapasReadRepo,
        TransferNapasUpdateRepo,
        TransferNapasDeleteRepo {
}
