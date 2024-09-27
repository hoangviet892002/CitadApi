package HDBanktraining.CitadApi.services.OtpServices;

import HDBanktraining.CitadApi.dtos.response.OtpResponse;
import HDBanktraining.CitadApi.entities.OtpEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.repository.OtpRepo.OtpRepo;
import HDBanktraining.CitadApi.services.OtpServices.impl.OtpServiceImpl;
import HDBanktraining.CitadApi.services.TransactionServices.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    void testInsertOtp_Success() throws Exception {
        // Arrange
        TransactionEntity transactionEntity = new TransactionEntity();
        OtpEntity mockOtpEntity = new OtpEntity();
        mockOtpEntity.setOtp("123456");

        when(otpRepo.save(any(OtpEntity.class))).thenReturn(mockOtpEntity);

        // Act
        Mono<OtpResponse> responseMono = otpService.insertOtp(transactionEntity);

        // Assert
        responseMono.subscribe(response -> {
            assertNotNull(response);
            assertEquals("123456", response.getOtp());

            try {
                verify(scheduler, times(1)).scheduleJob(any(JobDetail.class), any(Trigger.class));
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testCheckOtp_Success() throws Exception {
        // Arrange
        OtpEntity mockOtpEntity = new OtpEntity();
        mockOtpEntity.setOtp("123456");
        mockOtpEntity.setActive(true);

        when(otpRepo.findOtpByTransactionId("transaction123")).thenReturn(mockOtpEntity);

        // Act
        Mono<Boolean> result = otpService.checkOtp("transaction123", "123456");

        // Assert
        result.subscribe(res -> {
            assertTrue(res);

            try {
                verify(scheduler, times(1)).deleteJob(any(JobKey.class));
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testCheckOtp_Fail_InvalidOtp() throws Exception {
        // Arrange
        OtpEntity mockOtpEntity = new OtpEntity();
        mockOtpEntity.setOtp("654321");
        mockOtpEntity.setActive(true);

        when(otpRepo.findOtpByTransactionId("transaction123")).thenReturn(mockOtpEntity);

        // Act
        Mono<Boolean> result = otpService.checkOtp("transaction123", "123456");

        // Assert
        result.subscribe(res -> {
            assertFalse(res);
        });

        verify(scheduler, never()).deleteJob(any(JobKey.class));
    }

    @Test
    void testCheckOtp_Fail_OtpNotFound() throws Exception {
        // Arrange
        when(otpRepo.findOtpByTransactionId("transaction123")).thenReturn(null); // OTP không tồn tại

        // Act
        Mono<Boolean> result = otpService.checkOtp("transaction123", "123456");

        // Assert
        result.subscribe(res -> {
            assertFalse(res); // Không tìm thấy OTP
        });

        // Verify rằng scheduler không được gọi
        verify(scheduler, never()).deleteJob(any(JobKey.class));
    }

    @Test
    void testCheckOtp_Fail_OtpInactive() throws Exception {
        // Arrange
        OtpEntity mockOtpEntity = new OtpEntity();
        mockOtpEntity.setOtp("123456");
        mockOtpEntity.setActive(false); // OTP đã hết hạn

        when(otpRepo.findOtpByTransactionId("transaction123")).thenReturn(mockOtpEntity);

        // Act
        Mono<Boolean> result = otpService.checkOtp("transaction123", "123456");

        // Assert
        result.subscribe(res -> {
            assertFalse(res); // OTP không còn active
        });

        // Verify scheduler không được gọi
        verify(scheduler, never()).deleteJob(any(JobKey.class));
    }

}
