package HDBanktraining.CitadApi.controllers.Transaction.Patch;


import HDBanktraining.CitadApi.dtos.request.AcceptTransferRequest;
import HDBanktraining.CitadApi.dtos.response.AcceptTransferResponse;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.services.TransactionServices.TransactionService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<BaseReponse<AcceptTransferResponse>>> acceptTransfer(@RequestBody AcceptTransferRequest acceptTransferRequest) {
        return transactionService.acceptTransfer(acceptTransferRequest)
                .map(baseResponse -> {
                    if (baseResponse.getResponseCode().equals(ResponseEnum.DATA_NOT_FOUND.getResponseCode())) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
                    } else if (baseResponse.getResponseCode().equals(ResponseEnum.BIZ_ERROR.getResponseCode())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
                    } else if (baseResponse.getResponseCode().equals(ResponseEnum.SUCCESS.getResponseCode())) {
                        return ResponseEntity.ok(baseResponse);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
                    }
                });
    }
}
