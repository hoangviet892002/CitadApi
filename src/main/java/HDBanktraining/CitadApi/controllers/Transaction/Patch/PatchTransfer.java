package HDBanktraining.CitadApi.controllers.Transaction.Patch;


import HDBanktraining.CitadApi.dtos.request.AcceptTransferRequest;
import HDBanktraining.CitadApi.dtos.response.AcceptTransferResponse;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.services.TransactionServices.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@SecurityRequirement(name = "Api-Key")
@Tag(name = "Transaction")
@RequestMapping("/api/v1/transaction")
public class PatchTransfer {
    private final TransactionService transactionService;

    public PatchTransfer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PatchMapping("/accept")
    public Mono<BaseReponse<AcceptTransferResponse>> acceptTransfer(@RequestBody AcceptTransferRequest acceptTransferRequest) {
        return transactionService.acceptTransfer(acceptTransferRequest);
    }
}
