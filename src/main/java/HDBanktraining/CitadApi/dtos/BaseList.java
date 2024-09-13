package HDBanktraining.CitadApi.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseList <T>{
    @JsonProperty("page")
    private int page;
    @JsonProperty("size")
    private int size;
    @JsonProperty("totalPage")
    private int totalPage;
    @JsonProperty("totalRecord")
    private int totalRecord;
    @JsonProperty("data")
    private List<T> data;
}
