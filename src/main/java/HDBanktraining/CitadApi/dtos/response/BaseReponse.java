package HDBanktraining.CitadApi.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseReponse <T>{
    @JsonProperty("response_code")
    private String responseCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private T data;
}
