package HDBanktraining.CitadApi.services.TransferTransactionServices;

import HDBanktraining.CitadApi.repository.TransferTransactionRepo.TransferTransactionRepo;
import HDBanktraining.CitadApi.services.TranferTransactionService.impl.TranferTransactionImpl;
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
public class TransferTransactionServiceTest {

    @Autowired
    private TranferTransactionImpl tranferTransaction;

    @MockBean
    private TransferTransactionRepo transferTransactionRepo;

    @BeforeEach
    void setUp() {}

    @Test
    public void testInsert_Success() {}

    @Test
    public void testGetTransactionById_Success() {}

    @Test
    public void testGetTransactionById_NotFound() {}


}
