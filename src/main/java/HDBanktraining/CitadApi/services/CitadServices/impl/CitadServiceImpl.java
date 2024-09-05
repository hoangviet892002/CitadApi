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
        return validateRequest(page, size).flatMap(
                baseReponse -> {
                    if (baseReponse.getMessage() != null) {
                        return Mono.just(baseReponse);
                    }

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
                        baseReponse.setMessage(ResponseEnum.INTERNAL_ERROR.getMessage());
                        baseReponse.setResponseCode(ResponseEnum.INTERNAL_ERROR.getResponseCode());
                        return Mono.just(baseReponse);
                    }
                }
        );
    }

    public Mono<BaseReponse<BaseList<CitadReponse>>> validateRequest(String page, String size) {
        logger.info("Validating request");
        BaseReponse<BaseList<CitadReponse>> validateResponse = new BaseReponse<>();
        if (page == null || size == null) {
            validateResponse.setMessage(ResponseEnum.BAD_REQUEST.getMessage());
            validateResponse.setResponseCode(ResponseEnum.BAD_REQUEST.getResponseCode());
            logger.error("Error when validate request");
            return Mono.just(validateResponse);
        }
        try {
            Integer.parseInt(page);
            Integer.parseInt(size);
            return Mono.empty();
        } catch (Exception e) {
            validateResponse.setMessage(ResponseEnum.BAD_REQUEST.getMessage());
            validateResponse.setResponseCode(ResponseEnum.BAD_REQUEST.getResponseCode());
            logger.error("Error when validate request");
            return Mono.just(validateResponse);
        }
    }
}
