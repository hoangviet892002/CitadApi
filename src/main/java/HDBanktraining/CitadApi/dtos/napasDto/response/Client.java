package HDBanktraining.CitadApi.dtos.napasDto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @JsonProperty
    private String clientName;

    @JsonProperty
    private String accountNumber;
}
