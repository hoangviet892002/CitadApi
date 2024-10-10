package HDBanktraining.CitadApi.controllers.Napas.Patch;


import HDBanktraining.CitadApi.dtos.request.AcceptTransferRequest;
import HDBanktraining.CitadApi.dtos.response.AcceptTransferResponse;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.services.NapasServices.NapasService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Napas")
@RequestMapping("/api/v1/napas")
@SecurityRequirement(name = "Api-Key")
public class NapasPatchTransfer {
    private  final NapasService napasService;

    public NapasPatchTransfer(NapasService napasService) {
        this.napasService = napasService;
    }

    @PatchMapping("/accept")
    public Mono<ResponseEntity<BaseReponse<AcceptTransferResponse>>> acceptTransfer( @RequestBody AcceptTransferRequest acceptTransferRequest) {
        return napasService.acceptTransfer(acceptTransferRequest)
                .map(baseResponse -> {
                   if (baseResponse.getResponseCode().equals("BIZ_ERROR")) {
                        return ResponseEntity.badRequest().body(baseResponse);
                    } else if (baseResponse.getResponseCode().equals("SUCCESS")) {
                        return ResponseEntity.ok(baseResponse);
                    } else {
                        return ResponseEntity.internalServerError().body(baseResponse);
                    }
                });
    }


}
