package HDBanktraining.CitadApi.services.TransferTransactionServices;

import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.entities.TransferTransactionEntity;
import HDBanktraining.CitadApi.repository.TransferTransactionRepo.TransferTransactionRepo;
import HDBanktraining.CitadApi.services.TranferTransactionService.impl.TranferTransactionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "HOST=123",
        "USERNAME=test",
        "PASSWORD=testpassword"
})
public class TransferTransactionServiceTest {

    @Autowired
    private TranferTransactionImpl tranferTransaction;

    @MockBean
    private TransferTransactionRepo transferTransactionRepo;

    private ClientEntity receiver;
    private TransactionEntity transaction;

    @BeforeEach
    void setUp() {
        receiver = new ClientEntity();
        receiver.setId("receiverId");
        receiver.setActive(true);

        transaction = new TransactionEntity();
        transaction.setId("transactionId");
        transaction.setAmount(1000.0);
    }

    @Test
    public void testInsert_Success() {
        // Arrange
        TransferTransactionEntity transferTransactionEntity = new TransferTransactionEntity(receiver, transaction);

        when(transferTransactionRepo.save(any(TransferTransactionEntity.class))).thenReturn(transferTransactionEntity);

        // Act
        tranferTransaction.insert(transaction, receiver).subscribe();

        // Assert
        ArgumentCaptor<TransferTransactionEntity> captor = ArgumentCaptor.forClass(TransferTransactionEntity.class);
        verify(transferTransactionRepo, times(1)).save(captor.capture());

        TransferTransactionEntity savedEntity = captor.getValue();
        assertEquals(transaction.getId(), savedEntity.getTransaction().getId());
        assertEquals(receiver.getId(), savedEntity.getReceiver().getId());
    }

    @Test
    public void testGetTransactionById_Success() {
        // Arrange
        TransferTransactionEntity transferTransactionEntity = new TransferTransactionEntity(receiver, transaction);

        when(transferTransactionRepo.findByTransactionId("transactionId")).thenReturn(transferTransactionEntity);

        // Act
        Mono<TransferTransactionEntity> result = tranferTransaction.getTransactionById("transactionId");

        // Assert
        result.subscribe(transfer -> {
            assertNotNull(transfer);
            assertEquals("transactionId", transfer.getTransaction().getId());
            assertEquals("receiverId", transfer.getReceiver().getId());
        });

        verify(transferTransactionRepo, times(1)).findByTransactionId("transactionId");
    }

    @Test
    public void testGetTransactionById_NotFound() {
        // Arrange
        when(transferTransactionRepo.findByTransactionId("invalidId")).thenReturn(null);

        // Act
        Mono<TransferTransactionEntity> result = tranferTransaction.getTransactionById("invalidId");

        // Assert
        result.subscribe(transfer -> {
            assertNull(transfer);
        });

        verify(transferTransactionRepo, times(1)).findByTransactionId("invalidId");
    }

}
