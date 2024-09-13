package HDBanktraining.CitadApi.services.CitadServices.operations;

import HDBanktraining.CitadApi.entities.CitadEntity;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface InsertCitadService {
    Mono<Void> checkAndSaveCitadData() throws IOException;

    Mono<Void> insertCitadData(CitadEntity citadEntity) throws IOException;
}
