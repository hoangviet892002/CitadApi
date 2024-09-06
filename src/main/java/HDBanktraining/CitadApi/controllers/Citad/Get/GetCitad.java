package HDBanktraining.CitadApi.controllers.Citad.Get;


import HDBanktraining.CitadApi.dtos.response.BaseList;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import HDBanktraining.CitadApi.services.CitadServices.CitadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Mono<BaseReponse<BaseList<CitadReponse>>> getListCitad(@RequestParam String page, @RequestParam String size) {
        return citadService.queryCitads(page, size);
    }
}
