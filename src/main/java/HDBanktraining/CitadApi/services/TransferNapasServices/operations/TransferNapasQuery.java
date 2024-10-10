package HDBanktraining.CitadApi.services.TransferNapasServices.operations;

import HDBanktraining.CitadApi.entities.TransferNapasEntity;
import HDBanktraining.CitadApi.entities.TransferTransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface TransferNapasQuery {
    Mono<TransferNapasEntity> getTransactionById(String id);
}
