package HDBanktraining.CitadApi.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseReponse <T>{
    @JsonProperty("response_code")
    private String responseCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private T data;
}
