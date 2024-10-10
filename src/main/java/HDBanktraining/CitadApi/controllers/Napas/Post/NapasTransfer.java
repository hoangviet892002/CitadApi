package HDBanktraining.CitadApi.controllers.Napas.Post;

import HDBanktraining.CitadApi.dtos.napasDto.request.NapasTransferRequest;
import HDBanktraining.CitadApi.dtos.napasDto.response.NapasTransferResponse;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.services.NapasServices.NapasService;
import HDBanktraining.CitadApi.services.TransactionServices.TransactionService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Napas")
@RequestMapping("/api/v1/napas")
@SecurityRequirement(name = "Api-Key")
public class NapasTransfer {
    private  final NapasService napasService;

    public NapasTransfer(NapasService napasService) {
        this.napasService = napasService;
    }


    @PostMapping("/transfer")
    public Mono<ResponseEntity<BaseReponse<NapasTransferResponse>>> transfer(@RequestBody NapasTransferRequest NapasTransferRequest) {
        return napasService.transfer(NapasTransferRequest)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response));
    }

}
