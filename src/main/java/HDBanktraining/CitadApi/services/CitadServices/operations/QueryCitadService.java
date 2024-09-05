package HDBanktraining.CitadApi.services.CitadServices.operations;

import HDBanktraining.CitadApi.dtos.response.BaseList;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import reactor.core.publisher.Mono;

public interface QueryCitadService {
    Mono<BaseReponse<BaseList<CitadReponse>>> queryCitads(String page, String size);
}
