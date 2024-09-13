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
public class Bank {
    @JsonProperty
    private String bankName;
    @JsonProperty
    private String bankCode;
}
