package HDBanktraining.CitadApi.services.ClientServices.operations;

import HDBanktraining.CitadApi.entities.CitadEntity;
import reactor.core.publisher.Mono;

public interface InsertClientService {

    Mono<Void> insertClient(CitadEntity citadEntity);
}
