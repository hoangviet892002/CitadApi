package HDBanktraining.CitadApi.controllers.Citad.Get;

import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import HDBanktraining.CitadApi.services.CitadServices.impl.CitadServiceImpl;
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
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "HOST=123",
        "USERNAME=test",
        "PASSWORD=testpassword"
})
public class GetCitadTest {

    @Autowired
    private GetCitad getCitad;

    @MockBean
    private CitadServiceImpl citadServiceImpl;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(getCitad).build();
    }

    @Test
    public void testGetListCitad_Success() {
        // Arrange
        CitadReponse citadReponse1 = new CitadReponse("123", "test1", "testbranch1");
        CitadReponse citadReponse2 = new CitadReponse("233", "test2", "testbranch2");

        BaseList<CitadReponse> citadReponseBaseList = new BaseList<>();
        citadReponseBaseList.setPage(1);
        citadReponseBaseList.setSize(10);
        citadReponseBaseList.setTotalPage(1);
        citadReponseBaseList.setTotalRecord(2);
        citadReponseBaseList.setData(Arrays.asList(citadReponse1, citadReponse2));

        BaseReponse<BaseList<CitadReponse>> response = new BaseReponse<>();
        response.setData(citadReponseBaseList);
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        response.setResponseCode(ResponseEnum.SUCCESS.getResponseCode());

        when(citadServiceImpl.queryCitads(anyString(), anyString())).thenReturn(Mono.just(response));

        // Act & Assert
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/citad")
                        .queryParam("page", "1")
                        .queryParam("size", "10")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<BaseReponse<BaseList<CitadReponse>>>() {})
                .value(res -> {
                    BaseList<CitadReponse> returnList = res.getData();

                    assertEquals(2, returnList.getTotalRecord());
                    assertEquals(1, returnList.getPage());
                    assertEquals(10, returnList.getSize());
                    assertEquals(2, returnList.getData().size());

                    assertEquals("123", returnList.getData().get(0).getCode());
                    assertEquals("test1", returnList.getData().get(0).getName());
                    assertEquals("testbranch1", returnList.getData().get(0).getBranch());

                    assertEquals("233", returnList.getData().get(1).getCode());
                    assertEquals("test2", returnList.getData().get(1).getName());
                    assertEquals("testbranch2", returnList.getData().get(1).getBranch());

                });

        Mockito.verify(citadServiceImpl, Mockito.times(1)).queryCitads("1", "10");
    }

    @Test
    public void testGetListCitad_InvalidPageSize() {
        // Arrange
        BaseReponse<BaseList<CitadReponse>> errorResponse = new BaseReponse<>();
        errorResponse.setMessage(ResponseEnum.BAD_REQUEST.getMessage());
        errorResponse.setResponseCode(ResponseEnum.BAD_REQUEST.getResponseCode());

        when(citadServiceImpl.queryCitads("Invalid", "Invalid")).thenReturn(Mono.just(errorResponse));

        // Act & Assert
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/citad")
                        .queryParam("page", "Invalid")
                        .queryParam("size", "Invalid")
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(new ParameterizedTypeReference<BaseReponse<BaseList<CitadReponse>>>() {})
                .value(res -> {
                    assertNull(res.getData());
                    assertEquals(ResponseEnum.BAD_REQUEST.getResponseCode(), res.getResponseCode());
                });

        Mockito.verify(citadServiceImpl, Mockito.times(1)).queryCitads("Invalid", "Invalid");
    }

    @Test
    public void testGetListCitad_NoData() {
        // Arrange
        BaseList<CitadReponse> emptyList = new BaseList<>();
        emptyList.setPage(1);
        emptyList.setSize(10);
        emptyList.setTotalPage(0);
        emptyList.setTotalRecord(0);
        emptyList.setData(Collections.emptyList());

        BaseReponse<BaseList<CitadReponse>> emptyResponse = new BaseReponse<>();
        emptyResponse.setMessage(ResponseEnum.RESOURCE_NOT_FOUND.getMessage());
        emptyResponse.setResponseCode(ResponseEnum.RESOURCE_NOT_FOUND.getResponseCode());
        emptyResponse.setData(emptyList);

        when(citadServiceImpl.queryCitads("1", "10")).thenReturn(Mono.just(emptyResponse));

        // Act & Assert
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/citad")
                        .queryParam("page", "1")
                        .queryParam("size", "10")
                        .build())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(new ParameterizedTypeReference<BaseReponse<BaseList<CitadReponse>>>() {})
                .value(res -> {
                    assertNotNull(res.getData());
                    assertTrue(res.getData().getData().isEmpty());

                    assertEquals(ResponseEnum.RESOURCE_NOT_FOUND.getMessage(), res.getMessage());
                    assertEquals(ResponseEnum.RESOURCE_NOT_FOUND.getResponseCode(), res.getResponseCode());
                });
    }
}
