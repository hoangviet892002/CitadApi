package HDBanktraining.CitadApi.controllers.Transaction.Patch;

import HDBanktraining.CitadApi.dtos.request.AcceptTransferRequest;
import HDBanktraining.CitadApi.dtos.response.AcceptTransferResponse;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.services.TransactionServices.impl.TransactionServiceImpl;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "HOST=123",
        "USERNAME=test",
        "PASSWORD=testpassword"
})
public class PatchTransferTest {

    @Autowired
    private PatchTransfer patchTransfer;

    @MockBean
    private TransactionServiceImpl transactionService;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(patchTransfer).build();
    }

    @Test
    public void testAcceptTransfer_Success() {
        // Arrange
        AcceptTransferRequest request = new AcceptTransferRequest();
        request.setTransactionId("abcdef");
        request.setOtp("123456");

        AcceptTransferResponse response = new AcceptTransferResponse();
        BaseReponse<AcceptTransferResponse> baseResponse = new BaseReponse<>();
        baseResponse.setResponseCode(ResponseEnum.SUCCESS.getResponseCode());
        baseResponse.setMessage(ResponseEnum.SUCCESS.getMessage());
        baseResponse.setData(response);

        when(transactionService.acceptTransfer(any(AcceptTransferRequest.class))).thenReturn(Mono.just(baseResponse));

        // Act & Assert
        webTestClient.patch()
                .uri("/api/v1/transaction/accept")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BaseReponse.class)
                .value(res -> {
                    assert res.getResponseCode().equals(ResponseEnum.SUCCESS.getResponseCode());
                    assert res.getMessage().equals(ResponseEnum.SUCCESS.getMessage());
                });

        Mockito.verify(transactionService, Mockito.times(1)).acceptTransfer(any(AcceptTransferRequest.class));
    }

    @Test
    public void testAcceptTransfer_DataNotFound() {
        // Arrange
        AcceptTransferRequest request = new AcceptTransferRequest();
        request.setTransactionId("abcdef");
        request.setOtp("123456");

        BaseReponse<AcceptTransferResponse> baseResponse = new BaseReponse<>();
        baseResponse.setResponseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode());
        baseResponse.setMessage(ResponseEnum.DATA_NOT_FOUND.getMessage());

        when(transactionService.acceptTransfer(any(AcceptTransferRequest.class))).thenReturn(Mono.just(baseResponse));

        // Act & Assert
        webTestClient.patch()
                .uri("/api/v1/transaction/accept")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(BaseReponse.class)
                .value(res -> {
                    assert res.getResponseCode().equals(ResponseEnum.DATA_NOT_FOUND.getResponseCode());
                    assert res.getMessage().equals(ResponseEnum.DATA_NOT_FOUND.getMessage());
                });

        Mockito.verify(transactionService, Mockito.times(1)).acceptTransfer(any(AcceptTransferRequest.class));
    }

    @Test
    public void testAcceptTransfer_BusinessError() {
        // Arrange
        AcceptTransferRequest request = new AcceptTransferRequest();
        request.setTransactionId("abcdef");
        request.setOtp("123456");

        BaseReponse<AcceptTransferResponse> baseResponse = new BaseReponse<>();
        baseResponse.setResponseCode(ResponseEnum.BIZ_ERROR.getResponseCode());
        baseResponse.setMessage(ResponseEnum.BIZ_ERROR.getMessage());

        when(transactionService.acceptTransfer(any(AcceptTransferRequest.class))).thenReturn(Mono.just(baseResponse));

        // Act & Assert
        webTestClient.patch()
                .uri("/api/v1/transaction/accept")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(BaseReponse.class)
                .value(res -> {
                    assert res.getResponseCode().equals(ResponseEnum.BIZ_ERROR.getResponseCode());
                    assert res.getMessage().equals(ResponseEnum.BIZ_ERROR.getMessage());
                });

        Mockito.verify(transactionService, Mockito.times(1)).acceptTransfer(any(AcceptTransferRequest.class));
    }

    @Test
    public void testAcceptTransfer_InternalServerError() {
        // Arrange
        AcceptTransferRequest request = new AcceptTransferRequest();
        request.setTransactionId("abcdef");
        request.setOtp("123456");

        BaseReponse<AcceptTransferResponse> baseResponse = new BaseReponse<>();
        baseResponse.setResponseCode("9999");
        baseResponse.setMessage("Unknown error");

        when(transactionService.acceptTransfer(any(AcceptTransferRequest.class))).thenReturn(Mono.just(baseResponse));

        // Act & Assert
        webTestClient.patch()
                .uri("/api/v1/transaction/accept")
                .bodyValue(request)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(BaseReponse.class)
                .value(res -> {
                    assert res.getResponseCode().equals("9999");
                    assert res.getMessage().equals("Unknown error");
                });

        Mockito.verify(transactionService, Mockito.times(1)).acceptTransfer(any(AcceptTransferRequest.class));
    }

}
