package HDBanktraining.CitadApi.controllers.Napas.Get;

import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.napasDto.response.Bank;
import HDBanktraining.CitadApi.dtos.napasDto.response.Client;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.services.NapasServices.impl.NapasServiceImpl;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "HOST=123",
        "USERNAME=test",
        "PASSWORD=testpassword"
})
public class GetNapasInformationTest {

    @Autowired
    private GetNapasInformation getNapasInformation;

    @MockBean
    private NapasServiceImpl napasService;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(getNapasInformation).build();
    }

    @Test
    public void testGetClient_Success() {
        // Arrange
        Client client = new Client("Tuan", "12345");
        BaseReponse<Client> response = new BaseReponse<>();
        response.setData(client);
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        response.setResponseCode(ResponseEnum.SUCCESS.getResponseCode());

        when(napasService.getClient("12345", "000")).thenReturn(Mono.just(response));

        // Act & Assert
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/napas/client")
                        .queryParam("number", "12345")
                        .queryParam("bankCode", "000")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<BaseReponse<Client>>() {})
                .value(res -> {
                    assertEquals("12345", res.getData().getAccountNumber());
                    assertEquals("Tuan", res.getData().getClientName());
                    assertEquals(ResponseEnum.SUCCESS.getMessage(), res.getMessage());
                    assertEquals(ResponseEnum.SUCCESS.getResponseCode(), res.getResponseCode());
                });

        Mockito.verify(napasService, Mockito.times(1)).getClient("12345", "000");
    }

    @Test
    public void testGetClient_NotFound() {
        // Arrange
        BaseReponse<Client> response = new BaseReponse<>();
        response.setMessage(ResponseEnum.DATA_NOT_FOUND.getMessage());
        response.setResponseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode());

        when(napasService.getClient("12345", "000")).thenReturn(Mono.just(response));

        // Act & Assert
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/napas/client")
                        .queryParam("number", "12345")
                        .queryParam("bankCode", "000")
                        .build())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(new ParameterizedTypeReference<BaseReponse<Client>>() {})
                .value(res -> {
                    assertNull(res.getData());
                    assertEquals(ResponseEnum.DATA_NOT_FOUND.getMessage(), res.getMessage());
                });

        Mockito.verify(napasService, Mockito.times(1)).getClient("12345", "000");
    }

    @Test
    public void testGetClient_InvalidInput() {
        // Arrange
        BaseReponse<Client> errorResponse = new BaseReponse<>();
        errorResponse.setMessage(ResponseEnum.BAD_REQUEST.getMessage());
        errorResponse.setResponseCode(ResponseEnum.BAD_REQUEST.getResponseCode());

        when(napasService.getClient("InvalidNumber", "InvalidBank")).thenReturn(Mono.just(errorResponse));

        // Act & Assert
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/napas/client")
                        .queryParam("number", "InvalidNumber")
                        .queryParam("bankCode", "InvalidBank")
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(new ParameterizedTypeReference<BaseReponse<Client>>() {})
                .value(res -> {
                    assertNull(res.getData());
                    assertEquals(ResponseEnum.BAD_REQUEST.getMessage(), res.getMessage());
                });

        Mockito.verify(napasService, Mockito.times(1)).getClient("InvalidNumber", "InvalidBank");
    }

    @Test
    public void testGetBanks_Success() {
        // Arrange
        Bank bank1 = new Bank("Name1", "000");
        Bank bank2 = new Bank("Name2", "001");
        BaseList<Bank> bankList = new BaseList<>();
        bankList.setPage(1);
        bankList.setSize(10);
        bankList.setTotalRecord(2);
        bankList.setData(Arrays.asList(bank1, bank2));

        BaseReponse<BaseList<Bank>> response = new BaseReponse<>();
        response.setData(bankList);
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        response.setResponseCode(ResponseEnum.SUCCESS.getResponseCode());

        when(napasService.getBanks()).thenReturn(Mono.just(response));

        // Act & Assert
        webTestClient.get()
                .uri("/api/v1/napas/banks")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<BaseReponse<BaseList<Bank>>>() {})
                .value(res -> {
                    assertEquals(2, res.getData().getTotalRecord());
                    assertEquals("Name1", res.getData().getData().get(0).getBankName());
                    assertEquals(ResponseEnum.SUCCESS.getMessage(), res.getMessage());
                });

        Mockito.verify(napasService, Mockito.times(1)).getBanks();
    }

    @Test
    public void testGetBanks_NotFound() {
        // Arrange
        BaseReponse<BaseList<Bank>> response = new BaseReponse<>();
        response.setMessage(ResponseEnum.DATA_NOT_FOUND.getMessage());
        response.setResponseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode());

        when(napasService.getBanks()).thenReturn(Mono.just(response));

        // Act & Assert
        webTestClient.get()
                .uri("/api/v1/napas/banks")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(new ParameterizedTypeReference<BaseReponse<BaseList<Bank>>>() {})
                .value(res -> {
                    assertNull(res.getData());
                    assertEquals(ResponseEnum.DATA_NOT_FOUND.getMessage(), res.getMessage());
                });

        Mockito.verify(napasService, Mockito.times(1)).getBanks();
    }
}
