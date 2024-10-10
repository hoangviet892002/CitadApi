package HDBanktraining.CitadApi.controllers.Citad.Get;


import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import HDBanktraining.CitadApi.services.CitadServices.CitadService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/citad")
@Tag(name = "Citad", description = "Citad API")
@SecurityRequirement(name = "Api-Key")
public class GetCitad {

    private final CitadService citadService;




    @Autowired
    public GetCitad(CitadService citadService) {
        this.citadService = citadService;
    }

    @GetMapping
    @Operation(summary = "Get list citad", description = "Get list citad")
    public Mono<ResponseEntity<BaseReponse<BaseList<CitadReponse>>>>  getListCitad(@RequestParam String page, @RequestParam String size) {


        return citadService.queryCitads(page, size)
                .map(response -> {
                    if (response.getResponseCode().equals(ResponseEnum.SUCCESS.getResponseCode())) {
                        return ResponseEntity.ok(response);
                    } else if (response.getResponseCode().equals(ResponseEnum.BAD_REQUEST.getResponseCode())) {
                        return ResponseEntity.badRequest().body(response);
                    } else if (response.getResponseCode().equals(ResponseEnum.RESOURCE_NOT_FOUND.getResponseCode())) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                    }
                });
    }
}
