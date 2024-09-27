package HDBanktraining.CitadApi.services.TransactionServices;

import HDBanktraining.CitadApi.dtos.request.DataTransferRequest;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.OtpResponse;
import HDBanktraining.CitadApi.dtos.response.TransferResponse;
import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.repository.TransactionRepo.TransactionRepo;
import HDBanktraining.CitadApi.repository.TransferTransactionRepo.TransferTransactionRepo;
import HDBanktraining.CitadApi.services.ClientServices.ClientService;
import HDBanktraining.CitadApi.services.OtpServices.OtpService;
import HDBanktraining.CitadApi.services.TranferTransactionService.impl.TranferTransactionImpl;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "HOST=123",
        "USERNAME=test",
        "PASSWORD=testpassword"
})
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepo transactionRepo;

    @MockBean
    private TranferTransactionImpl tranferTransaction;

    @MockBean
    private TransferTransactionRepo transferTransactionRepo;

    @MockBean
    private ClientService clientService;

    @MockBean
    private OtpService otpService;

    private DataTransferRequest dataTransferRequest;
    private ClientEntity sender;
    private ClientEntity receiver;
    private TransactionEntity transactionEntity;
    private OtpResponse otpResponse;

    @BeforeEach
    void setUp() {
        // Setup test data
        sender = new ClientEntity();
        sender.setId("senderId");
        sender.setActive(true);

        receiver = new ClientEntity();
        receiver.setId("receiverId");
        receiver.setActive(true);

        transactionEntity = new TransactionEntity();
        transactionEntity.setId("transactionId");
        transactionEntity.setAmount(1000.0);
        transactionEntity.setSender(sender);

        otpResponse = new OtpResponse();
        otpResponse.setOtp("123456");

        dataTransferRequest = new DataTransferRequest();
        dataTransferRequest.setSender("senderId");
        dataTransferRequest.setReceiver("receiverId");
        dataTransferRequest.setAmount(1000.0);
        dataTransferRequest.setMessage("Test Transaction");
    }

    @Test
    void testInsertTransaction_Success() {
        // Arrange
        when(clientService.getClientById(dataTransferRequest.getSender())).thenReturn(Mono.just(sender));
        when(clientService.getClientById(dataTransferRequest.getReceiver())).thenReturn(Mono.just(receiver));
        when(clientService.checkBalance(sender.getId(), dataTransferRequest.getAmount())).thenReturn(Mono.just(true));
        when(transactionRepo.save(any(TransactionEntity.class))).thenReturn(transactionEntity);
        when(tranferTransaction.insert(any(TransactionEntity.class), any(ClientEntity.class))).thenReturn(Mono.empty());
        when(otpService.insertOtp(any(TransactionEntity.class))).thenReturn(Mono.just(otpResponse));

        // Act
        Mono<BaseReponse<TransferResponse>> result = transactionService.insertTransaction(dataTransferRequest);

        // Assert
        BaseReponse<TransferResponse> response = result.block();
        assertNotNull(response);
        assertEquals(ResponseEnum.SUCCESS.getResponseCode(), response.getResponseCode());
        assertEquals(otpResponse.getOtp(), response.getData().getOtp());
    }

    @Test
    void testInsertTransaction_Failed_ClientNotFound() {
        // Arrange
        when(clientService.getClientById(dataTransferRequest.getSender())).thenReturn(Mono.empty());
        when(clientService.getClientById(dataTransferRequest.getReceiver())).thenReturn(Mono.just(receiver));

        // Act
        Mono<BaseReponse<TransferResponse>> result = transactionService.insertTransaction(dataTransferRequest);

        // Assert
        BaseReponse<TransferResponse> response = result.block();
        assertNotNull(response);
        assertEquals(ResponseEnum.DATA_NOT_FOUND.getResponseCode(), response.getResponseCode());
    }

    @Test
    void testInsertTransaction_Failed_OtpServiceError() {
        // Arrange
        when(clientService.getClientById(dataTransferRequest.getSender())).thenReturn(Mono.just(sender));
        when(clientService.getClientById(dataTransferRequest.getReceiver())).thenReturn(Mono.just(receiver));
        when(clientService.checkBalance(sender.getId(), dataTransferRequest.getAmount())).thenReturn(Mono.just(true));
        when(transactionRepo.save(any(TransactionEntity.class))).thenReturn(transactionEntity);
        when(tranferTransaction.insert(any(TransactionEntity.class), any(ClientEntity.class))).thenReturn(Mono.empty());
        when(otpService.insertOtp(any(TransactionEntity.class))).thenReturn(Mono.error(new RuntimeException("OTP Service error")));

        // Act
        Mono<BaseReponse<TransferResponse>> result = transactionService.insertTransaction(dataTransferRequest);

        // Assert
        BaseReponse<TransferResponse> response = result.block();
        assertNotNull(response);
        assertEquals(ResponseEnum.INTERNAL_ERROR.getResponseCode(), response.getResponseCode());
    }

    @Test
    void testQueryTransaction_Success() {
        // Arrange
        when(transactionRepo.findById("transactionId")).thenReturn(transactionEntity);

        // Act
        Mono<TransactionEntity> result = transactionService.queryTransaction("transactionId");

        // Assert
        TransactionEntity transaction = result.block();
        assertNotNull(transaction);
        assertEquals("transactionId", transaction.getId());
    }
}
