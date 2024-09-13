package HDBanktraining.CitadApi.dtos.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AcceptTransferRequest {
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("otp")
    private String otp;
}
