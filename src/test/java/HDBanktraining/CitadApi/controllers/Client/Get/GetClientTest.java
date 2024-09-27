package HDBanktraining.CitadApi.controllers.Client.Get;

import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import HDBanktraining.CitadApi.dtos.response.ClientResponse;
import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.mappers.ClientMappers;
import HDBanktraining.CitadApi.repository.ClientRepo.ClientRepo;
import HDBanktraining.CitadApi.services.ClientServices.impl.ClientServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "HOST=123",
        "USERNAME=test",
        "PASSWORD=testpassword"
})
public class GetClientTest {

    @Autowired
    private GetClient getClient;

    @MockBean
    private ClientServiceImpl clientService;

    @MockBean
    private ClientRepo clientRepo;

    @MockBean
    private ClientMappers clientMappers;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(getClient).build();
    }

    @Test
    public void testGetClientByNumber_Success() {
        // Arrange
        ClientResponse client = new ClientResponse();
        client.setId("abcd147f258g369d");
        client.setNumber("01234");
        client.setWallet(100000);
        client.setName("Le Quang Tuan");
        client.setDob("11-09-2001");
        client.setAddress("Bien Hoa Dong Nai");
        client.setEmail("abc@gmail.com");
        client.setPhone("113");
        client.setCitad("vf4Dasd46Sasd");

        when(clientService.getClientByNumber("01234")).thenReturn(Mono.just(new BaseReponse<>(
                ResponseEnum.SUCCESS.getResponseCode(),
                ResponseEnum.SUCCESS.getMessage(),
                client
        )));

        // Act & Assert
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/client")
                        .queryParam("number", "01234")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<BaseReponse<ClientResponse>>() {})
                .value(res -> {
                    assertEquals("0000", res.getResponseCode());
                    assertEquals("Success", res.getMessage());
                    assertNotNull(res.getData());

                    ClientResponse responseData = res.getData();
                    assertEquals("abcd147f258g369d", responseData.getId());
                    assertEquals("01234", responseData.getNumber());
                    assertEquals(100000, responseData.getWallet());
                    assertEquals("Le Quang Tuan", responseData.getName());
                    assertEquals("11-09-2001", responseData.getDob());
                    assertEquals("Bien Hoa Dong Nai", responseData.getAddress());
                    assertEquals("abc@gmail.com", responseData.getEmail());
                    assertEquals("113", responseData.getPhone());
                    assertEquals("vf4Dasd46Sasd", responseData.getCitad());
                });

        Mockito.verify(clientService, Mockito.times(1)).getClientByNumber("01234");
    }


    @Test
    public void testGetClientByNumber_InvalidNumber() {
        // Arrange
        when(clientService.getClientByNumber("Invalid")).thenReturn(Mono.just(new BaseReponse<>(
                ResponseEnum.BAD_REQUEST.getResponseCode(),
                ResponseEnum.BAD_REQUEST.getMessage(),
                null
        )));
        // Act & Assert
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/client")
                        .queryParam("number", "Invalid")
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(new ParameterizedTypeReference<BaseReponse<ClientResponse>>() {})
                .value(res -> {

                    assertEquals(ResponseEnum.BAD_REQUEST.getMessage(), res.getMessage());
                    assertEquals(ResponseEnum.BAD_REQUEST.getResponseCode(), res.getResponseCode());
                    assertNull(res.getData());

                });
        Mockito.verify(clientService, Mockito.times(1)).getClientByNumber("Invalid");
    }

    @Test
    public void testGetClientByNumer_NotFound() {
        // Arrange
        when(clientService.getClientByNumber("12345")).thenReturn(Mono.just(new BaseReponse<>(
                ResponseEnum.DATA_NOT_FOUND.getResponseCode(),
                ResponseEnum.DATA_NOT_FOUND.getMessage(),
                null
        )));

        // Act & Assert
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/client")
                        .queryParam("number", "12345")
                        .build())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody((new ParameterizedTypeReference<BaseReponse<ClientResponse>>() {}))
                .value(res -> {

                    assertEquals(ResponseEnum.DATA_NOT_FOUND.getMessage(), res.getMessage());
                    assertEquals(ResponseEnum.DATA_NOT_FOUND.getResponseCode(), res.getResponseCode());
                    assertNull(res.getData());

                });
    }
}
