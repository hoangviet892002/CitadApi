package HDBanktraining.CitadApi.shared.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TransactionStatusEnum {
    PENDING("PENDING", "Transaction is pending"),
    SUCCESS("SUCCESS", "Transaction is successful"),
    FAILED("FAILED", "Transaction is failed");

    private final String value;
    private final String description;
}
