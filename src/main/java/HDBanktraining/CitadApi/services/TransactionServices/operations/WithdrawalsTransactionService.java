package HDBanktraining.CitadApi.services.TransactionServices.operations;

import HDBanktraining.CitadApi.dtos.request.DataTransferRequest;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.TransferResponse;
import reactor.core.publisher.Mono;

public interface WithdrawalsTransactionService {
    Mono<BaseReponse<TransferResponse>> withDrawals(DataTransferRequest dataTransferRequest);
}
