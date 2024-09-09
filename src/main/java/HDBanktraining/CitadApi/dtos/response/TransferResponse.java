package HDBanktraining.CitadApi.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferResponse {
    @JsonProperty("message")
    private String message;
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("otp")
    private String otp;
}
