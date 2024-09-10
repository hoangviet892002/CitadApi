package HDBanktraining.CitadApi.services.CitadServices.operations;

import reactor.core.publisher.Mono;

import java.io.IOException;

public interface InsertCitadService {
    Mono<Void> checkAndSaveCitadData() throws IOException;
}
