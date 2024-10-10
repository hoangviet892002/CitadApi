package HDBanktraining.CitadApi.controllers.Transaction.Get;


import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.TransactionResponse;
import HDBanktraining.CitadApi.services.TransactionServices.TransactionService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@SecurityRequirement(name = "Api-Key")
@Tag(name = "Transaction")
@RequestMapping("/api/v1/transaction")
public class GetTransaction {

    private final TransactionService transactionService;

    public GetTransaction(TransactionService transactionService) {
        this.transactionService = transactionService;
    }



    @GetMapping()
    public Mono<BaseReponse<BaseList<TransactionResponse>>> getTransaction(
            @RequestParam String accountID,
            @RequestParam String page,
            @RequestParam String size) {



        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        // Thực hiện logic lấy dữ liệu giao dịch

       BaseList<TransactionResponse> responseBaseList = transactionService.queryTransactions(accountID, pageable).block();

       if (responseBaseList == null) {
           return Mono.just(BaseReponse.<BaseList<TransactionResponse>>builder().responseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode()).message(ResponseEnum.DATA_NOT_FOUND.getMessage()).build());
       }

        return Mono.just(BaseReponse.<BaseList<TransactionResponse>>builder().responseCode(ResponseEnum.SUCCESS.getResponseCode()).message(ResponseEnum.SUCCESS.getMessage()).data(responseBaseList).build());


    }

}
