package HDBanktraining.CitadApi.controllers.Citad.Get;

import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import HDBanktraining.CitadApi.services.CitadServices.CitadService;
import HDBanktraining.CitadApi.services.CitadServices.impl.CitadServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

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

//    @Test
//    public void testGetListCitad_Success() {}
//
//    @Test
//    public void testGetListCitad_Failure() {}

    @Test
    public void testGetListCitad_Success() {
        // Create a sample response
        BaseList<CitadReponse> baseList = new BaseList<>();
        baseList.setData(Collections.singletonList(new CitadReponse("123", "Sample Citad", "sample branch")));

        BaseReponse<BaseList<CitadReponse>> baseReponse = new BaseReponse<>();
        baseReponse.setData(baseList);

        // Mock the service call
        Mockito.when(citadServiceImpl.queryCitads("1", "10"))
                .thenReturn(Mono.just(baseReponse));

        // Perform the GET request
        webTestClient.get()
                .uri("/api/v1/citad?page=1&size=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.data[0].code").isEqualTo("123")
                .jsonPath("$.data.data[0].name").isEqualTo("Sample Citad")
                .jsonPath("$.data.data[0].branch").isEqualTo("Sample branch");
    }

//    @Test
//    public void testGetListCitad_Failure() {
//        // Mock the service to return an empty Mono
//        Mockito.when(citadServiceImpl.queryCitads("1", "10"))
//                .thenReturn(Mono.empty());
//
//        // Perform the GET request and expect a 404 status or other handling of no data
//        webTestClient.get()
//                .uri("/api/v1/citad?page=1&size=10")
//                .exchange()
//                .expectStatus().isNotFound();
//    }
}
