package HDBanktraining.CitadApi.services.TransactionServices.impl;


import HDBanktraining.CitadApi.dtos.request.AcceptTransferRequest;
import HDBanktraining.CitadApi.dtos.request.DataTransferRequest;
import HDBanktraining.CitadApi.dtos.response.AcceptTransferResponse;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.OtpResponse;
import HDBanktraining.CitadApi.dtos.response.TransferResponse;
import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.entities.OtpEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.entities.TransferTransactionEntity;
import HDBanktraining.CitadApi.repository.TransactionRepo.TransactionRepo;
import HDBanktraining.CitadApi.repository.TransferTransactionRepo.TransferTransactionRepo;
import HDBanktraining.CitadApi.services.ClientServices.ClientService;
import HDBanktraining.CitadApi.services.OtpServices.OtpService;
import HDBanktraining.CitadApi.services.TranferTransactionService.TranferTransactionService;
import HDBanktraining.CitadApi.services.TransactionServices.TransactionService;
import HDBanktraining.CitadApi.shared.enums.BussinessExeption;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import HDBanktraining.CitadApi.shared.enums.TransactionEnum;
import HDBanktraining.CitadApi.shared.enums.TransactionStatusEnum;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;

    private final TranferTransactionService tranferTransactionService;
    private final ClientService clientService;
    private final OtpService otpService;
    private static final Logger logger = Logger.getLogger(TransactionServiceImpl.class);

    public TransactionServiceImpl(TransactionRepo transactionRepo, TransferTransactionRepo transferTransactionRepo, TranferTransactionService tranferTransactionService, ClientService clientService,@Lazy OtpService otpService) {
        this.transactionRepo = transactionRepo;
        this.tranferTransactionService = tranferTransactionService;
        this.clientService = clientService;
        this.otpService = otpService;
    }

    @Override
    public Mono<BaseReponse<TransferResponse>> insertTransaction(DataTransferRequest dataTransferRequest) {
        BaseReponse<TransferResponse> baseReponse = new BaseReponse<TransferResponse>();
        try {
            ClientEntity sender = clientService.getClientById(dataTransferRequest.getSender()).block();
            ClientEntity receiver = clientService.getClientById(dataTransferRequest.getReceiver()).block();
            Mono<BaseReponse<TransferResponse>> validate = checkValidateTransaction(sender, receiver, dataTransferRequest.getAmount());
            if (validate.block() != null) {
                return validate;
            }

            TransactionEntity transactionEntity = new TransactionEntity(
                    dataTransferRequest.getAmount(),
                    dataTransferRequest.getMessage(),
                    TransactionStatusEnum.PENDING.getValue(),
                    TransactionEnum.TRANSFER.getValue(),
                    sender
            );
            TransactionEntity newTransaction = transactionRepo.save(transactionEntity);
            tranferTransactionService.insert(newTransaction,receiver);
            OtpResponse otp = otpService.insertOtp(newTransaction).block();
            assert otp != null;
            TransferResponse transferResponse = new TransferResponse(transactionEntity.getDescription(), transactionEntity.getId(), otp.getOtp());
            baseReponse.setData(transferResponse);

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

    private  Mono<BaseReponse<TransferResponse>> checkValidateTransaction(ClientEntity sender, ClientEntity receiver, double amount) {
        if (sender == null || receiver == null) {
            return Mono.just(BaseReponse.<TransferResponse>builder().
                    responseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode())
                    .message(ResponseEnum.DATA_NOT_FOUND.getMessage()).build());
        }
        if (!sender.isActive()){
            return Mono.just(BaseReponse.<TransferResponse>builder().
                    responseCode(ResponseEnum.BIZ_ERROR.getResponseCode())
                    .message(BussinessExeption.ACCOUNT_NOT_ACTIVE.getMessage()).build());
        }
        if (!receiver.isActive()){
            return Mono.just(BaseReponse.<TransferResponse>builder().
                    responseCode(ResponseEnum.BIZ_ERROR.getResponseCode())
                    .message(BussinessExeption.ACCOUNT_NOT_ACTIVE.getMessage()).build());
        }
        boolean checkBalance = Boolean.TRUE.equals(clientService.checkBalance(sender.getId(), amount).block());
        if (!checkBalance) {
            return Mono.just(BaseReponse.<TransferResponse>builder().
                    responseCode(ResponseEnum.BIZ_ERROR.getResponseCode())
                    .message(BussinessExeption.ACCOUNT_NOT_BALANCE.getMessage()).build());
        }
        return Mono.empty();
    }

    @Override
    public Mono<TransactionEntity> queryTransaction(String transactionId) {
        return Mono.just(transactionRepo.findById(transactionId));
    }

    @Override
    public Mono<Void> updateTransaction(TransactionEntity transaction) {
        transactionRepo.save(transaction);
        return Mono.empty();
    }

    @Override
    public Mono<BaseReponse<TransferResponse>> resendOtp(String transactionId) {
        BaseReponse<TransferResponse> baseReponse = new BaseReponse<TransferResponse>();
        try {
            TransactionEntity transaction = transactionRepo.findById(transactionId);
            if (transaction == null) {
                baseReponse.setResponseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode());
                baseReponse.setMessage(ResponseEnum.DATA_NOT_FOUND.getMessage());
                return Mono.just(baseReponse);
            }
            // find Otp of transaction
            OtpResponse otp = otpService.resetOtp(transaction).block();
            assert otp != null;
            TransferResponse transferResponse = new TransferResponse(transaction.getDescription(), transaction.getId(), otp.getOtp());
            baseReponse.setData(transferResponse);
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

    @Override
    public Mono<BaseReponse<AcceptTransferResponse>> acceptTransfer(AcceptTransferRequest acceptTransferRequest) {
            // find transaction
            TransactionEntity transaction = transactionRepo.findById(acceptTransferRequest.getTransactionId());
            if (transaction == null) {
                return Mono.just(BaseReponse.<AcceptTransferResponse>builder().
                        responseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode())
                        .message(ResponseEnum.DATA_NOT_FOUND.getMessage()).build());
            }
            // find otp
        boolean checkOtp = false;
        try {
            checkOtp = Boolean.TRUE.equals(otpService.checkOtp(transaction.getId(), acceptTransferRequest.getOtp()).block());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!checkOtp) {
            return Mono.just(BaseReponse.<AcceptTransferResponse>builder().
                    responseCode(ResponseEnum.BIZ_ERROR.getResponseCode())
                    .message(BussinessExeption.OTP_NOT_CORRECT.getMessage()).build());
        }
        // find Transfer
        TransferTransactionEntity transfer = tranferTransactionService.getTransactionById(transaction.getId()).block();
        if (transfer == null) {
            updateStatusTransaction(transaction, TransactionStatusEnum.FAILED).block();
            return Mono.just(BaseReponse.<AcceptTransferResponse>builder().
                    responseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode())
                    .message(ResponseEnum.DATA_NOT_FOUND.getMessage()).build());
        }
        // get sender and receiver
        ClientEntity sender =  transaction.getSender();
        ClientEntity receiver = transfer.getReceiver();
        //check validate account not null or active
        if (sender == null || receiver == null) {
            updateStatusTransaction(transaction, TransactionStatusEnum.FAILED).block();
            return Mono.just(BaseReponse.<AcceptTransferResponse>builder().
                    responseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode())
                    .message(ResponseEnum.DATA_NOT_FOUND.getMessage()).build());
        }
        if (!sender.isActive() || !receiver.isActive()){
            updateStatusTransaction(transaction, TransactionStatusEnum.FAILED).block();
            return Mono.just(BaseReponse.<AcceptTransferResponse>builder().
                    responseCode(ResponseEnum.BIZ_ERROR.getResponseCode())
                    .message(BussinessExeption.ACCOUNT_NOT_ACTIVE.getMessage()).build());
        }


        // check balance
        boolean checkBalance = Boolean.TRUE.equals(clientService.checkBalance(sender.getId(), transaction.getAmount()).block());
        if (!checkBalance) {
            updateStatusTransaction(transaction, TransactionStatusEnum.FAILED).block();
            return Mono.just(BaseReponse.<AcceptTransferResponse>builder().
                    responseCode(ResponseEnum.BIZ_ERROR.getResponseCode())
                    .message(BussinessExeption.ACCOUNT_NOT_BALANCE.getMessage()).build());
        }

        Mono.zip(
                // update balance
                clientService.updateBalance(sender, transaction.getAmount(), Boolean.FALSE),
                clientService.updateBalance(receiver, transaction.getAmount(), Boolean.TRUE),
                // update status transaction
                updateStatusTransaction(transaction, TransactionStatusEnum.SUCCESS)).then()
                .doOnError(e -> {
                    updateStatusTransaction(transaction, TransactionStatusEnum.FAILED).block();
                    logger.error(e.getMessage());
                }).subscribe();

        // done
        return Mono.just(BaseReponse.<AcceptTransferResponse>builder().
                responseCode(ResponseEnum.SUCCESS.getResponseCode())
                .message(ResponseEnum.SUCCESS.getMessage()).build());

    }

    public  Mono<Void> updateStatusTransaction(TransactionEntity transaction, TransactionStatusEnum status) {
        transaction.setStatus(status.getValue());
        transactionRepo.save(transaction);
        return Mono.empty();
    }
}