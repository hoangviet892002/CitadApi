package HDBanktraining.CitadApi.services.OtpServices;

import HDBanktraining.CitadApi.repository.OtpRepo.OtpRepo;
import HDBanktraining.CitadApi.services.OtpServices.impl.OtpServiceImpl;
import HDBanktraining.CitadApi.services.TransactionServices.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class OtpServiceTest {

    @Autowired
    private OtpServiceImpl otpService;

    @MockBean
    private OtpRepo otpRepo;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    @Qualifier("scheduler")
    private Scheduler scheduler;

    @BeforeEach
    void setUp() {}

    @Test
    void testInsertOtp_Success() throws Exception {}

    @Test
    void testCheckOtp_Success() throws Exception {}

    @Test
    void testCheckOtp_Fail_InvalidOtp() throws Exception {}
}
