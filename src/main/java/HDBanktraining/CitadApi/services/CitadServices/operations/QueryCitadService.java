package HDBanktraining.CitadApi.services.CitadServices.operations;

import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import HDBanktraining.CitadApi.entities.CitadEntity;
import reactor.core.publisher.Mono;

public interface QueryCitadService {
    Mono<BaseReponse<BaseList<CitadReponse>>> queryCitads(String page, String size);
    Mono<CitadEntity> queryCitad(String code);
}
