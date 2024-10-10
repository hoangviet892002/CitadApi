package HDBanktraining.CitadApi.services.NapasServices.impl;


import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.napasDto.request.NapasTransferRequest;
import HDBanktraining.CitadApi.dtos.napasDto.response.Bank;
import HDBanktraining.CitadApi.dtos.napasDto.response.Client;
import HDBanktraining.CitadApi.dtos.napasDto.response.NapasTransferResponse;
import HDBanktraining.CitadApi.dtos.request.AcceptTransferRequest;
import HDBanktraining.CitadApi.dtos.response.AcceptTransferResponse;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.entities.TransferNapasEntity;
import HDBanktraining.CitadApi.services.NapasServices.NapasService;
import HDBanktraining.CitadApi.services.TransactionServices.TransactionService;
import HDBanktraining.CitadApi.services.TransferNapasServices.TransferNapasService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class NapasServiceImpl  implements NapasService {

    private static final Logger log = Logger.getLogger(NapasServiceImpl.class);
    @Value("${spring.napas.url}")
    private String NAPAS_API;
    @Autowired
    private RestTemplate restTemplate;

    private final TransactionService transactionService;
    private final TransferNapasService transferNapasService;

    public NapasServiceImpl(TransactionService transactionService, TransferNapasService transferNapasService) {
        this.transactionService = transactionService;
        this.transferNapasService = transferNapasService;
    }

    @Override
    public Mono<BaseReponse<BaseList<Bank>>> getBanks() {
        BaseList<Bank> banks = restTemplate.getForObject(NAPAS_API + "bank", BaseList.class);
        if (Objects.isNull(banks)) {
            return Mono.just(BaseReponse.<BaseList<Bank>>builder().responseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode()).message(ResponseEnum.DATA_NOT_FOUND.getMessage()).build());
        }
        return Mono.just(BaseReponse.<BaseList<Bank>>builder().responseCode(ResponseEnum.SUCCESS.getResponseCode()).message(ResponseEnum.SUCCESS.getMessage()).data(banks).build());
    }

    @Override
    public Mono<BaseReponse<Client>> getClient(String number, String bankCode) {


        Client client = restTemplate.getForObject(NAPAS_API + "client?number=" + number + "&bank=" + bankCode, Client.class);

        if (Objects.isNull(client)) {
            return Mono.just(BaseReponse.<Client>builder()
                    .responseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode())
                    .message(ResponseEnum.DATA_NOT_FOUND.getMessage())
                    .build());
        }
        return Mono.just(BaseReponse.<Client>builder()
                .responseCode(ResponseEnum.SUCCESS.getResponseCode())
                .message(ResponseEnum.SUCCESS.getMessage()).data(client)
                .build());
    }

    @Override
    public Mono<BaseReponse<NapasTransferResponse>> transfer(NapasTransferRequest napasTransferRequest) {
        // check account of receiver
        BaseReponse<Client> client = getClient(napasTransferRequest.getReceiver(), napasTransferRequest.getBankCode()).block();
        if (Objects.isNull(client) || Objects.isNull(client.getData())) {
            return Mono.just(BaseReponse.<NapasTransferResponse>builder()
                    .responseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode())
                    .message("Receiver account not found.")
                    .build());
        }
        // send to transaction service
        return transactionService.insertTransactionNapas(napasTransferRequest);
    }

    @Override
    public Mono<BaseReponse<AcceptTransferResponse>> acceptTransfer(AcceptTransferRequest acceptTransferRequest) {

        //get TransferNapasEntity  by id transaction
        TransferNapasEntity transferNapasEntity = transferNapasService.getTransactionById(acceptTransferRequest.getTransactionId()).block();

        log.info("transferNapasEntity account: " + transferNapasEntity.getReceiverAccount());
        //check account of receiver
        BaseReponse<Client> client = getClient(transferNapasEntity.getReceiverAccount(), transferNapasEntity.getReceiverBank()).block();
        if (Objects.isNull(client) || Objects.isNull(client.getData())) {
            return Mono.just(BaseReponse.<AcceptTransferResponse>builder()
                    .responseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode())
                    .message("Receiver account not found.")
                    .build());
        }

        //send to transaction service
        return transactionService.acceptTransferNapas(acceptTransferRequest);
    }
}
