package HDBanktraining.CitadApi.shared.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BussinessExeption {
    BANK_NOT_FOUND("BANK_NOT_FOUND", "Bank not found"),
    CLIENT_NOT_FOUND("CLIENT_NOT_FOUND", "Client not found"),
    CLIENT_ALREADY_EXISTS("CLIENT_ALREADY_EXISTS", "Client already exists"),
    BANK_ALREADY_EXISTS("BANK_ALREADY_EXISTS", "Bank already exists"),
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND", "Account not found"),
    ACCOUNT_ALREADY_EXISTS("ACCOUNT_ALREADY_EXISTS", "Account already exists"),
    ACCOUNT_NOT_ACTIVE("ACCOUNT_NOT_ACTIVE", "Account not active"),
    ACCOUNT_RECEIVER_NOT_FOUND("ACCOUNT_RECEIVER_NOT_FOUND", "Account receiver not found"),
    ACCOUNT_RECEIVER_NOT_ACTIVE("ACCOUNT_RECEIVER_NOT_ACTIVE", "Account receiver not active"),
    ACCOUNT_NOT_BALANCE("ACCOUNT_NOT_BALANCE", "Account not balance"),
    ACCOUNT_NOT_ACTIVE_OR_BALANCE("ACCOUNT_NOT_ACTIVE_OR_BALANCE", "Account not active or balance"),
    OTP_NOT_CORRECT("OTP_NOT_CORRECT", "Otp not correct");
    private final String message;
    private final String description;
}
