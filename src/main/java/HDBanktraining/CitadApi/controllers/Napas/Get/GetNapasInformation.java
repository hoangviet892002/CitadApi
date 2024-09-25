package HDBanktraining.CitadApi.controllers.Napas.Get;


import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.napasDto.response.Bank;
import HDBanktraining.CitadApi.dtos.napasDto.response.Client;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.services.NapasServices.NapasService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @GetMapping
    public Mono<BaseReponse<BaseList<Bank>>> getBanks() {
        return napasService.getBanks();
    }

    @GetMapping("/client")
    public Mono<BaseReponse<Client>> getClient(@RequestParam String number, @RequestParam String bankCode) {
        return napasService.getClient(number, bankCode);
    }
}
