package HDBanktraining.CitadApi.services.TransactionServices.operations;

import HDBanktraining.CitadApi.dtos.request.AcceptTransferRequest;
import HDBanktraining.CitadApi.dtos.request.DataTransferRequest;
import HDBanktraining.CitadApi.dtos.response.AcceptTransferResponse;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.TransferResponse;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import reactor.core.publisher.Mono;

public interface UpdateTransactionService {
    Mono<Void> updateTransaction(TransactionEntity transaction);

    Mono<BaseReponse<TransferResponse>> resendOtp(String transactionId);

    Mono<BaseReponse<AcceptTransferResponse>> acceptTransfer(AcceptTransferRequest acceptTransferRequest);

}
