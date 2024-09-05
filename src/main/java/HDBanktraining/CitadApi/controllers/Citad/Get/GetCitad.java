package HDBanktraining.CitadApi.controllers.Citad.Get;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/citad")
@Tag(name = "Citad", description = "Citad API")
@SecurityRequirement(name = "Api-Key")
public class GetCitad {

}
