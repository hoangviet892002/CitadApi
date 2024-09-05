package HDBanktraining.CitadApi.services.CitadServices.impl;

import HDBanktraining.CitadApi.controllers.testApi.Get.GetTestApi;
import HDBanktraining.CitadApi.dtos.response.BaseList;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import HDBanktraining.CitadApi.entities.CitadEntity;
import HDBanktraining.CitadApi.mappers.CitadMappers;
import HDBanktraining.CitadApi.repository.CitadRepo.CitadRepo;
import HDBanktraining.CitadApi.services.CitadServices.CitadService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class CitadServiceImpl implements CitadService {

    private final CitadRepo citadRepo;

    private final CitadMappers citadMappers;

    private static final Logger logger = Logger.getLogger(GetTestApi.class);

    public CitadServiceImpl(CitadRepo citadRepo, CitadMappers citadMappers) {
        this.citadRepo = citadRepo;
        this.citadMappers = citadMappers;
    }

    @Override
    public Mono<BaseReponse<BaseList<CitadReponse>>> queryCitads(String page, String size) {
        return validateRequest(page, size).then(Mono.deferContextual(ctx ->
                {

                    BaseReponse<BaseList<CitadReponse>> baseReponse = new BaseReponse<>();
                    try {
                        logger.info("Getting list citad");
                        BaseList<CitadReponse> baseList = new BaseList<>();
                        baseList.setPage(Integer.parseInt(page));
                        baseList.setSize(Integer.parseInt(size));
                        Page<CitadEntity> citadReponses = citadRepo.findAll(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size)));
                        logger.info("Get list citad success");
                        baseList.setTotalRecord((int) citadReponses.getTotalElements());
                        baseList.setTotalPage(citadReponses.getTotalPages());
                        baseList.setData(citadMappers.entityToCitadReponse(citadReponses.getContent()));
                        logger.info("Mapping data success");
                        baseReponse.setData(baseList);
                        baseReponse.setMessage(ResponseEnum.SUCCESS.getMessage());
                        baseReponse.setResponseCode(ResponseEnum.SUCCESS.getResponseCode());
                        logger.info("Return response");
                        return Mono.just(baseReponse);
                    } catch (Exception e) {
                        logger.error("Error when getting list citad", e);
                        baseReponse.setMessage(ResponseEnum.INTERNAL_ERROR.getMessage());
                        baseReponse.setResponseCode(ResponseEnum.INTERNAL_ERROR.getResponseCode());
                        return Mono.just(baseReponse);
                    }
                }));
    }

    public Mono<Void> validateRequest(String page, String size) {
        logger.info("Validating request");
        if (page == null || size == null) {
            return Mono.error(new Exception("Page or size is null"));
        }
        return Mono.empty();
    }
}
