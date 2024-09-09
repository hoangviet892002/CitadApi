package HDBanktraining.CitadApi.services.TransactionServices.impl;


import HDBanktraining.CitadApi.dtos.request.DataTransferRequest;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.OtpResponse;
import HDBanktraining.CitadApi.dtos.response.TransferResponse;
import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.repository.TransactionRepo.TransactionRepo;
import HDBanktraining.CitadApi.services.ClientServices.ClientService;
import HDBanktraining.CitadApi.services.OtpServices.OtpService;
import HDBanktraining.CitadApi.services.TransactionServices.TransactionService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import HDBanktraining.CitadApi.shared.enums.TransactionEnum;
import HDBanktraining.CitadApi.shared.enums.TransactionStatusEnum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final ClientService clientService;
    private final OtpService otpService;
    private static final Logger logger = Logger.getLogger(TransactionServiceImpl.class);

    public TransactionServiceImpl(TransactionRepo transactionRepo, ClientService clientService, OtpService otpService) {
        this.transactionRepo = transactionRepo;
        this.clientService = clientService;
        this.otpService = otpService;
    }

    @Override
    public Mono<BaseReponse<TransferResponse>> insertTransaction(DataTransferRequest dataTransferRequest) {
        BaseReponse<TransferResponse> baseReponse = new BaseReponse<TransferResponse>();
        try {
            boolean checkBalance = Boolean.TRUE.equals(clientService.checkBalance(dataTransferRequest.getSender(), dataTransferRequest.getAmount()).block());
            if (!checkBalance) {
                baseReponse.setResponseCode(ResponseEnum.INSUFFICIENT_BALANCE.getResponseCode());
                baseReponse.setMessage(ResponseEnum.INSUFFICIENT_BALANCE.getMessage());
                return Mono.just(baseReponse);
            }
            ClientEntity sender = clientService.getClientById(dataTransferRequest.getSender()).block();
            ClientEntity receiver = clientService.getClientById(dataTransferRequest.getReceiver()).block();
            if (sender == null || receiver == null) {
                baseReponse.setResponseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode());
                baseReponse.setMessage(ResponseEnum.DATA_NOT_FOUND.getMessage());
                return Mono.just(baseReponse);
            }
            TransactionEntity transactionEntity = new TransactionEntity(
                    dataTransferRequest.getAmount(),
                    TransactionEnum.TRANSFER.getValue(),
                    dataTransferRequest.getMessage(),
                    TransactionStatusEnum.PENDING.getValue(),
                    receiver,
                    sender);
            TransactionEntity newTransaction = transactionRepo.save(transactionEntity);
            OtpResponse otp = otpService.insertOtp(newTransaction).block();
            assert otp != null;
            TransferResponse transferResponse = new TransferResponse(transactionEntity.getDescription(), transactionEntity.getId(), otp.getOtp());
            baseReponse.setData(transferResponse);
            // set cron job for otp expired and close transaction

            baseReponse.setResponseCode(ResponseEnum.SUCCESS.getResponseCode());
            baseReponse.setMessage(ResponseEnum.SUCCESS.getMessage());
            return Mono.just(baseReponse);
        }catch (Exception e){
            logger.error(e.getMessage());
            baseReponse.setResponseCode(ResponseEnum.INTERNAL_ERROR.getResponseCode());
            baseReponse.setMessage(ResponseEnum.INTERNAL_ERROR.getMessage());
            return Mono.just(baseReponse);
        }

    }
}
