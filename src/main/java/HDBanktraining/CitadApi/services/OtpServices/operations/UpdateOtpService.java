package HDBanktraining.CitadApi.services.OtpServices.operations;

import HDBanktraining.CitadApi.dtos.response.OtpResponse;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import reactor.core.publisher.Mono;

public interface UpdateOtpService {
    void cleanupOtp(String otpId);

    Mono<OtpResponse> resetOtp(TransactionEntity transactionEntity);

}
