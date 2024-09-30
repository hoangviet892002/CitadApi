package HDBanktraining.CitadApi.services.CitadServices.impl;

import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import HDBanktraining.CitadApi.entities.CitadEntity;
import HDBanktraining.CitadApi.mappers.CitadMappers;
import HDBanktraining.CitadApi.repository.CitadRepo.CitadRepo;
import HDBanktraining.CitadApi.services.CitadServices.CitadService;
import HDBanktraining.CitadApi.services.SftpServices.SftpService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import HDBanktraining.CitadApi.utils.ExcelReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CitadServiceImpl implements CitadService {

    private static final String TEMP_FILE_PREFIX = "bank-code-list";

    private static final String TEMP_FILE_SUFFIX = ".xlsx";

    private static final Path RESOURCES_DIR = Paths.get("src/main/resources");

    private static final String REMOTE_FILE_PATH = "C:\\Users\\Administrator\\Documents\\bank-code-list.xlsx";

    private final CitadRepo citadRepo;

    @Autowired
    private ExcelReader excelReader;

    @Autowired
    private SftpService sftpService;

    private final CitadMappers citadMappers;

    private static final Logger logger = Logger.getLogger(CitadServiceImpl.class);

    public CitadServiceImpl(CitadRepo citadRepo, CitadMappers citadMappers) {
        this.citadRepo = citadRepo;
        this.citadMappers = citadMappers;
    }

    @Override
    public Mono<BaseReponse<BaseList<CitadReponse>>> queryCitads(String page, String size) {
        return validateRequest(page, size)
                .switchIfEmpty(
                        Mono.defer(() -> {
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
                                baseReponse.setMessage(ResponseEnum.INTERNAL_ERROR.getMessage());
                                baseReponse.setResponseCode(ResponseEnum.INTERNAL_ERROR.getResponseCode());
                                return Mono.just(baseReponse);
                            }
                        })
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
            logger.info("Validate request success");
            return Mono.empty();
        } catch (Exception e) {
            validateResponse.setMessage(ResponseEnum.BAD_REQUEST.getMessage());
            validateResponse.setResponseCode(ResponseEnum.BAD_REQUEST.getResponseCode());
            logger.error("Error when validate request");
            return Mono.just(validateResponse);
        }
    }

    @Override
    public Mono<Void> checkAndSaveCitadData() {
        return Mono.fromCallable(() -> {
                    logger.info("Citad list is updating");

                    Path tempFilePath = RESOURCES_DIR.resolve(TEMP_FILE_PREFIX + TEMP_FILE_SUFFIX);

                    if (Files.exists(tempFilePath)) {
                        Files.delete(tempFilePath);
                        logger.info("Old local file deleted.");
                    }

                    Files.createFile(tempFilePath);
                    return tempFilePath.toAbsolutePath().toString();
                })
                .flatMap(localFilePath -> {
                    return sftpService.downloadFile(REMOTE_FILE_PATH, localFilePath)
                            .then(Mono.just(localFilePath));
                })
                .flatMap(localFilePath -> {
                    List<CitadReponse> citadDTOs;
                    try {
                        citadDTOs = excelReader.readCitadFromExcel(localFilePath);
                    } catch (IOException e) {
                        return Mono.error(new RuntimeException("Failed to read Excel file", e));
                    }

                    Set<String> newFileCodes = citadDTOs.stream()
                            .map(CitadReponse::getCode)
                            .collect(Collectors.toSet());

                    return processCitadEntities(citadDTOs, newFileCodes);
                })
                .doOnError(e -> logger.error("Error while processing Citad data", e))
                .doOnSuccess(unused -> logger.info("Citad data sync completed successfully."));
    }

    public Mono<Void> processCitadEntities(List<CitadReponse> citadDTOs, Set<String> newFileCodes) {
        return Mono.fromRunnable(() -> {
            citadDTOs.forEach(citadDTO -> {
                CitadEntity existingEntity = citadRepo.findByCode(citadDTO.getCode());
                if (existingEntity == null) {
                    // Insert new citad code
                    CitadEntity newEntity = new CitadEntity();
                    newEntity.setCode(citadDTO.getCode());
                    newEntity.setName(citadDTO.getName());
                    newEntity.setBranch(citadDTO.getBranch());
                    newEntity.setActive(true);
                    citadRepo.save(newEntity);
                    logger.info("Citad code " + citadDTO.getCode() + " is created");
                } else {
                    // Update citad code
                    boolean isUpdated = false;
                    if (!existingEntity.getName().equals(citadDTO.getName())) {
                        existingEntity.setName(citadDTO.getName());
                        isUpdated = true;
                    }
                    if (!existingEntity.getBranch().equals(citadDTO.getBranch())) {
                        existingEntity.setBranch(citadDTO.getBranch());
                        isUpdated = true;
                    }

                    if (isUpdated) {
                        citadRepo.save(existingEntity);
                        logger.info("Citad code " + citadDTO.getCode() + " is updated");
                    }
                }
            });

            // Inactive with citad code deleted
            citadRepo.findAll().forEach(entity -> {
                if (!newFileCodes.contains(entity.getCode()) && entity.isActive()) {
                    entity.setActive(false);
                    citadRepo.save(entity);
                    logger.info("Citad code " + entity.getCode() + " is set to inactive in the database");
                }
            });
        });
    }

    @Override
    public Mono<CitadEntity> queryCitad(String code) {
        return Mono.justOrEmpty(citadRepo.findByCode(code));
    }

    @Override
    public Mono<Void> insertCitadData( CitadEntity citadEntity) throws IOException {

        CitadEntity existCitad = citadRepo.findByCode(citadEntity.getCode());
        if (existCitad != null) {
            logger.info("Citad already exist");
            return Mono.empty();
        }
        citadRepo.save(citadEntity);
        logger.info("Insert citad success");
        return Mono.empty();

    }
}

