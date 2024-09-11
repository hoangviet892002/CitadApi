package HDBanktraining.CitadApi.controllers.Transaction.Post;


import HDBanktraining.CitadApi.dtos.request.DataTransferRequest;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.TransferResponse;
import HDBanktraining.CitadApi.services.TransactionServices.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@SecurityRequirement(name = "Api-Key")
@Tag(name = "Transaction")
@RequestMapping("/api/v1/transaction")
public class Transfer {
    @Autowired
    private  TransactionService transactionService;
     @PostMapping("/transfer")
    public Mono<BaseReponse<TransferResponse>> transfer(@RequestBody DataTransferRequest dataTransferRequest) {
        return transactionService.insertTransaction(dataTransferRequest);
    }
}
