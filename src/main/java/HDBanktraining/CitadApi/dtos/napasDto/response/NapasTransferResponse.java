package HDBanktraining.CitadApi.dtos.napasDto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NapasTransferResponse {
    @JsonProperty("message")
    private String message;
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("otp")
    private String otp;


}
