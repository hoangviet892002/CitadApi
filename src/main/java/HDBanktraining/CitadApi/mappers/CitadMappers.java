package HDBanktraining.CitadApi.mappers;

import HDBanktraining.CitadApi.dtos.request.CitadRequest;
import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import HDBanktraining.CitadApi.entities.CitadEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CitadMappers {
    public CitadReponse entityToCitadReponse(CitadEntity citadEntity) {
        return new CitadReponse(citadEntity.getCode(), citadEntity.getName(), citadEntity.getBranch());
    }
    public List<CitadReponse> entityToCitadReponse(List<CitadEntity> citadEntities) {
        return citadEntities.stream().map(this::entityToCitadReponse).collect(Collectors.toList());
    }

    public CitadEntity citadRequestToEntity(CitadRequest citadRequest) {
        return new CitadEntity(citadRequest.getCode(), citadRequest.getName(), citadRequest.getBranch());
    }

    public List<CitadEntity> citadRequestToEntity(List<CitadRequest> citadRequests) {
        return citadRequests.stream().map(this::citadRequestToEntity).collect(Collectors.toList());
    }


}
