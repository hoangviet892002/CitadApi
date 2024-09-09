package HDBanktraining.CitadApi.shared.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionEnum {

    DEPOSIT("DEPOSIT", "Deposit money to your account"),
    TRANSFER("TRANSFER", "Transfer money to another account"),
    PAYMENT("PAYMENT", "Payment for services");

    private final String value;
    private final String description;
}
