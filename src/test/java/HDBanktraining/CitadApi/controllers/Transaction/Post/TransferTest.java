package HDBanktraining.CitadApi.controllers.Transaction.Post;

import HDBanktraining.CitadApi.controllers.Transaction.Patch.PatchTransfer;
import HDBanktraining.CitadApi.dtos.request.AcceptTransferRequest;
import HDBanktraining.CitadApi.dtos.request.DataTransferRequest;
import HDBanktraining.CitadApi.dtos.response.AcceptTransferResponse;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.TransferResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "HOST=123",
        "USERNAME=test",
        "PASSWORD=testpassword"
})
public class TransferTest {

    @Autowired
    private Transfer transfer;

    @MockBean
    private TransactionServiceImpl transactionService;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(transfer).build();
    }

    @Test
    void testTransfer_Success() {
        // Arrange
        DataTransferRequest dataTransferRequest = new DataTransferRequest();
        TransferResponse transferResponse = new TransferResponse();
        BaseReponse<TransferResponse> baseReponse = new BaseReponse<>();
        baseReponse.setResponseCode(ResponseEnum.SUCCESS.getResponseCode());
        baseReponse.setMessage(ResponseEnum.SUCCESS.getMessage());
        baseReponse.setData(transferResponse);

        when(transactionService.insertTransaction(any(DataTransferRequest.class))).thenReturn(Mono.just(baseReponse));

        // Act & Assert
        webTestClient.post()
                .uri("/api/v1/transaction/transfer")
                .contentType(APPLICATION_JSON)
                .bodyValue(dataTransferRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BaseReponse.class)
                .value(res -> {
                    assertEquals(ResponseEnum.SUCCESS.getResponseCode(), res.getResponseCode());
                    assertEquals(ResponseEnum.SUCCESS.getMessage(), res.getMessage());
                });

        Mockito.verify(transactionService, Mockito.times(1)).insertTransaction(any(DataTransferRequest.class));
    }

    @Test
    void testTransfer_DataNotFound() {
        // Arrange
        DataTransferRequest dataTransferRequest = new DataTransferRequest();
        BaseReponse<TransferResponse> baseReponse = new BaseReponse<>();
        baseReponse.setResponseCode(ResponseEnum.RESOURCE_NOT_FOUND.getResponseCode());
        baseReponse.setMessage(ResponseEnum.RESOURCE_NOT_FOUND.getMessage());

        when(transactionService.insertTransaction(any(DataTransferRequest.class))).thenReturn(Mono.just(baseReponse));

        // Act & Assert
        webTestClient.post()
                .uri("/api/v1/transaction/transfer")
                .contentType(APPLICATION_JSON)
                .bodyValue(dataTransferRequest)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(BaseReponse.class)
                .value(res -> {
                    assertEquals(ResponseEnum.RESOURCE_NOT_FOUND.getResponseCode(), res.getResponseCode());
                    assertEquals(ResponseEnum.RESOURCE_NOT_FOUND.getMessage(), res.getMessage());
                });

        Mockito.verify(transactionService, Mockito.times(1)).insertTransaction(any(DataTransferRequest.class));
    }

    @Test
    void testTransfer_BizError() {
        // Arrange
        DataTransferRequest dataTransferRequest = new DataTransferRequest();
        BaseReponse<TransferResponse> baseReponse = new BaseReponse<>();
        baseReponse.setResponseCode(ResponseEnum.BIZ_ERROR.getResponseCode());
        baseReponse.setMessage(ResponseEnum.BIZ_ERROR.getMessage());

        when(transactionService.insertTransaction(any(DataTransferRequest.class))).thenReturn(Mono.just(baseReponse));

        // Act & Assert
        webTestClient.post()
                .uri("/api/v1/transaction/transfer")
                .contentType(APPLICATION_JSON)
                .bodyValue(dataTransferRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(BaseReponse.class)
                .value(res -> {
                    assertEquals(ResponseEnum.BIZ_ERROR.getResponseCode(), res.getResponseCode());
                    assertEquals(ResponseEnum.BIZ_ERROR.getMessage(), res.getMessage());
                });

        Mockito.verify(transactionService, Mockito.times(1)).insertTransaction(any(DataTransferRequest.class));
    }

    @Test
    void testResendOtp_Success() {
        // Arrange
        String transactionId = "123";
        TransferResponse transferResponse = new TransferResponse();
        BaseReponse<TransferResponse> baseReponse = new BaseReponse<>();
        baseReponse.setResponseCode(ResponseEnum.SUCCESS.getResponseCode());
        baseReponse.setMessage(ResponseEnum.SUCCESS.getMessage());
        baseReponse.setData(transferResponse);

        when(transactionService.resendOtp(anyString())).thenReturn(Mono.just(baseReponse));

        // Act & Assert
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/transaction/resend-otp").queryParam("transactionId", transactionId).build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BaseReponse.class)
                .value(res -> {
                    assertEquals(ResponseEnum.SUCCESS.getResponseCode(), res.getResponseCode());
                    assertEquals(ResponseEnum.SUCCESS.getMessage(), res.getMessage());
                });

        Mockito.verify(transactionService, Mockito.times(1)).resendOtp(transactionId);
    }

    @Test
    void testResendOtp_DataNotFound() {
        // Arrange
        String transactionId = "123";
        BaseReponse<TransferResponse> baseReponse = new BaseReponse<>();
        baseReponse.setResponseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode());
        baseReponse.setMessage(ResponseEnum.DATA_NOT_FOUND.getMessage());

        when(transactionService.resendOtp(anyString())).thenReturn(Mono.just(baseReponse));

        // Act & Assert
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/transaction/resend-otp").queryParam("transactionId", transactionId).build())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(BaseReponse.class)
                .value(res -> {
                    assertEquals(ResponseEnum.DATA_NOT_FOUND.getResponseCode(), res.getResponseCode());
                    assertEquals(ResponseEnum.DATA_NOT_FOUND.getMessage(), res.getMessage());
                });

        Mockito.verify(transactionService, Mockito.times(1)).resendOtp(transactionId);
    }

}
