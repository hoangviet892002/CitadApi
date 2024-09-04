package HDBanktraining.CitadApi.controllers.testApi.Get;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Test API")
@RequestMapping("/api/v1/test")
public class GetTestApi {

     @GetMapping("/")
     @Operation(summary = "Test API", description = "Test API")
     public Mono<String> test() {
         return Mono.just("Hello");
     }
}
