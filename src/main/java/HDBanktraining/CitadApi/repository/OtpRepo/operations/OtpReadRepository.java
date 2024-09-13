package HDBanktraining.CitadApi.repository.OtpRepo.operations;

import HDBanktraining.CitadApi.entities.OtpEntity;

public interface OtpReadRepository {
    OtpEntity findOtpById(String otpId);
    OtpEntity findOtpByTransactionId(String transactionId);
}
