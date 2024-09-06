package HDBanktraining.CitadApi.controllers.Citad.Post;


import HDBanktraining.CitadApi.dtos.request.CitadRequest;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import HDBanktraining.CitadApi.services.CitadServices.CitadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Citad", description = "Citad API")
@RequestMapping("/api/v1/citad")
@SecurityRequirement(name = "Api-Key")
public class CreateCitad {

    private final CitadService citadService;

    public CreateCitad(CitadService citadService) {
        this.citadService = citadService;
    }


}
