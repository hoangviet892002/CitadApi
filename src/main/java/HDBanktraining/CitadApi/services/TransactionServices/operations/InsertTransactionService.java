package HDBanktraining.CitadApi.services.TransactionServices.operations;

import HDBanktraining.CitadApi.dtos.napasDto.request.NapasTransferRequest;
import HDBanktraining.CitadApi.dtos.napasDto.response.NapasTransferResponse;
import HDBanktraining.CitadApi.dtos.request.DataTransferRequest;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.TransferResponse;
import reactor.core.publisher.Mono;

public interface InsertTransactionService {
    Mono<BaseReponse<TransferResponse>> insertTransaction(DataTransferRequest dataTransferRequest);

    Mono<BaseReponse<NapasTransferResponse>> insertTransactionNapas(NapasTransferRequest napasTransferRequest);
}
