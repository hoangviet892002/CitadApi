package HDBanktraining.CitadApi.dtos.napasDto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class NapasTransferRequest {
    @JsonProperty("sender")
    private String sender;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("message")
    private String message;
    @JsonProperty("bank_code")
    private String bankCode;
    @JsonProperty("receiver")
    private String receiver;
}
