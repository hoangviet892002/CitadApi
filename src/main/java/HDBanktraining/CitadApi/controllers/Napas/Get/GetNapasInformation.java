package HDBanktraining.CitadApi.controllers.Napas.Get;


import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.napasDto.response.Bank;
import HDBanktraining.CitadApi.dtos.napasDto.response.Client;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.services.NapasServices.NapasService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Napas")
@RequestMapping("/api/v1/napas")
@SecurityRequirement(name = "Api-Key")
public class GetNapasInformation {
    private final NapasService napasService;

    public GetNapasInformation(NapasService napasService) {
        this.napasService = napasService;
    }

    @GetMapping("/banks")
    public Mono<ResponseEntity<BaseReponse<BaseList<Bank>>>> getBanks() {
        return napasService.getBanks()
                .map(response -> {
                    if (response.getResponseCode().equals(ResponseEnum.SUCCESS.getResponseCode())) {
                        return ResponseEntity.ok(response);
                    } else if (response.getResponseCode().equals(ResponseEnum.DATA_NOT_FOUND.getResponseCode())) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                    }
                });
    }


    @GetMapping("/client")
    public Mono<ResponseEntity<BaseReponse<Client>>> getClient(@RequestParam String number, @RequestParam String bankCode) {
        return napasService.getClient(number, bankCode)
                .map(response -> {
                    if (response.getResponseCode().equals(ResponseEnum.SUCCESS.getResponseCode())) {
                        return ResponseEntity.ok(response);
                    } else if (response.getResponseCode().equals(ResponseEnum.DATA_NOT_FOUND.getResponseCode())) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }else if (response.getResponseCode().equals(ResponseEnum.BAD_REQUEST.getResponseCode())) {
                        return ResponseEntity.badRequest().body(response);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                    }
                });
    }
}
