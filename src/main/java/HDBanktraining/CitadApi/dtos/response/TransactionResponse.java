package HDBanktraining.CitadApi.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("status")
    private String status;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("type")
    private String type;
}
