package HDBanktraining.CitadApi.services.TransactionServices;

import HDBanktraining.CitadApi.dtos.request.DataTransferRequest;
import HDBanktraining.CitadApi.dtos.response.OtpResponse;
import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.repository.TransactionRepo.TransactionRepo;
import HDBanktraining.CitadApi.repository.TransferTransactionRepo.TransferTransactionRepo;
import HDBanktraining.CitadApi.services.ClientServices.ClientService;
import HDBanktraining.CitadApi.services.OtpServices.OtpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

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
    void testInsertTransaction_Success() {}

    @Test
    void testInsertTransaction_Failed_ClientNotFound() {}

    @Test
    void testInsertTransaction_Failed_OtpServiceError() {}

    @Test
    void testQueryTransaction_Success() {}
}
