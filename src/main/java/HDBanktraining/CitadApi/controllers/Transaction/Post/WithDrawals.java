package HDBanktraining.CitadApi.controllers.Transaction.Post;

import HDBanktraining.CitadApi.dtos.request.DataTransferRequest;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.TransferResponse;
import HDBanktraining.CitadApi.services.TransactionServices.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@SecurityRequirement(name = "Api-Key")
@Tag(name = "Transaction")
@RequestMapping("/api/v1/transaction")
public class WithDrawals {

    private final TransactionService transactionService;

    public WithDrawals(TransactionService transactionService) {

        this.transactionService = transactionService;
    }

    @PostMapping("/withdrawals")
    public Mono<BaseReponse<TransferResponse>> withdrawals(
            @RequestBody DataTransferRequest dataTransferRequest,
            @RequestHeader("Authorization") String authToken) {
        // Xác thực User
        // Kiểm tra authorization
        // Xác thực 2 yếu tố
        // Xử lý giao dịch
        // Trả về thông báo
        return null;
    }
}
