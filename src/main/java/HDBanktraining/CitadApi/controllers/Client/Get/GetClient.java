package HDBanktraining.CitadApi.controllers.Client.Get;

import HDBanktraining.CitadApi.dtos.response.ClientResponse;
import HDBanktraining.CitadApi.services.ClientServices.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/client")
@Tag(name = "Client", description = "Client API")
@SecurityRequirement(name = "Api-Key")
public class GetClient {
    private final ClientService clientService;

    public GetClient (ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @Operation(summary = "Get client by number account", description = "Get client by number account")
    public Mono<ClientResponse> getClientByNumber(@RequestParam String number) {
        return clientService.getClientByNumber(number);
    }
}
