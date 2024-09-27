package HDBanktraining.CitadApi.services.NapasServices;

import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.napasDto.response.Bank;
import HDBanktraining.CitadApi.dtos.napasDto.response.Client;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.services.NapasServices.impl.NapasServiceImpl;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "HOST=123",
        "USERNAME=test",
        "PASSWORD=testpassword"
})
public class NapasServiceTest {

    @Autowired
    private NapasServiceImpl napasService;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {}

    @Test
    public void testGetBanks_Success() {
        // Arrange
        BaseList<Bank> mockBanks = new BaseList<>();
        when(restTemplate.getForObject("http://localhost:8089/api/v1/bank", BaseList.class)).thenReturn(mockBanks);

        // Act
        Mono<BaseReponse<BaseList<Bank>>> response = napasService.getBanks();

        // Assert
        response.subscribe(res -> {
            assertEquals(ResponseEnum.SUCCESS.getResponseCode(), res.getResponseCode());
            assertEquals(ResponseEnum.SUCCESS.getMessage(), res.getMessage());
            assertNotNull(res.getData());
            assertSame(mockBanks, res.getData());
        });
    }

    @Test
    public void testGetBanks_DataNotFound() {
        // Arrange
        when(restTemplate.getForObject("http://localhost:8089/api/v1/bank", BaseList.class)).thenReturn(null);

        // Act
        Mono<BaseReponse<BaseList<Bank>>> response = napasService.getBanks();

        // Assert
        response.subscribe(res -> {
            assertEquals(ResponseEnum.DATA_NOT_FOUND.getResponseCode(), res.getResponseCode());
            assertEquals(ResponseEnum.DATA_NOT_FOUND.getMessage(), res.getMessage());
            assertNull(res.getData());
        });
    }

    @Test
    public void testGetClient_Success() {
        // Arrange
        Client mockClient = new Client();
        when(restTemplate.getForObject("http://localhost:8089/api/v1/client?number=123456&bank=001", Client.class))
                .thenReturn(mockClient);

        // Act
        Mono<BaseReponse<Client>> response = napasService.getClient("123456", "001");

        // Assert
        response.subscribe(res -> {
            assertEquals(ResponseEnum.SUCCESS.getResponseCode(), res.getResponseCode());
            assertEquals(ResponseEnum.SUCCESS.getMessage(), res.getMessage());
            assertNotNull(res.getData());
            assertSame(mockClient, res.getData());
        });
    }

    @Test
    public void testGetClient_DataNotFound() {
        // Arrange
        when(restTemplate.getForObject("http://localhost:8089/api/v1/client?number=123456&bank=001", Client.class))
                .thenReturn(null);

        // Act
        Mono<BaseReponse<Client>> response = napasService.getClient("123456", "001");

        // Assert
        response.subscribe(res -> {
            assertEquals(ResponseEnum.DATA_NOT_FOUND.getResponseCode(), res.getResponseCode());
            assertEquals(ResponseEnum.DATA_NOT_FOUND.getMessage(), res.getMessage());
            assertNull(res.getData());
        });
    }

    @Test
    public void testGetClient_InvalidInput() {
        // Act
        Mono<BaseReponse<Client>> response = napasService.getClient("invalidNumber", "invalidBankCode");

        // Assert
        response.subscribe(res -> {
            assertEquals(ResponseEnum.BAD_REQUEST.getResponseCode(), res.getResponseCode());
            assertEquals("Invalid number or bankCode. Both must be numeric.", res.getMessage());
            assertNull(res.getData());
        });
    }
}
