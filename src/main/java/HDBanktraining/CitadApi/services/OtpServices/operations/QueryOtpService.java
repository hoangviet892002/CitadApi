package HDBanktraining.CitadApi.services.OtpServices.operations;

import HDBanktraining.CitadApi.entities.OtpEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import reactor.core.publisher.Mono;

public interface QueryOtpService {
    Mono<OtpEntity> findByTransactionId(TransactionEntity transactionId);

    Mono<Boolean> checkOtp(String transactionId, String otp) throws Exception;
}
