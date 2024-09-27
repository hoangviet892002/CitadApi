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
public class AcceptTransferResponse {

    @JsonProperty("transactionId")
    private Long transactionId;

    @JsonProperty("status")
    private String status;

}
