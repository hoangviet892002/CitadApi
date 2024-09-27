package HDBanktraining.CitadApi.controllers.Client.Get;

import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.ClientResponse;
import HDBanktraining.CitadApi.services.ClientServices.ClientService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<BaseReponse<ClientResponse>>> getClientByNumber(@RequestParam String number) {
        return clientService.getClientByNumber(number)
                .map(response -> {
                    if (ResponseEnum.BAD_REQUEST.getResponseCode().equals(response.getResponseCode())) {
                        return ResponseEntity.badRequest().body(response);
                    } else if (ResponseEnum.DATA_NOT_FOUND.getResponseCode().equals(response.getResponseCode())) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    } else {
                        return ResponseEntity.ok(response);
                    }
                });
    }
}
