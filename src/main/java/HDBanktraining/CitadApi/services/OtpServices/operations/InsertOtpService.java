package HDBanktraining.CitadApi.services.OtpServices.operations;

import HDBanktraining.CitadApi.dtos.response.OtpResponse;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import reactor.core.publisher.Mono;

public interface InsertOtpService {
    Mono<OtpResponse> insertOtp(TransactionEntity transactionEntity);
}
