package HDBanktraining.CitadApi.services.NapasServices;

import HDBanktraining.CitadApi.services.NapasServices.impl.NapasServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

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
    public void testGetBanks_Success() {}

    @Test
    public void testGetBanks_DataNotFound() {}

    @Test
    public void testGetClient_Success() {}

    @Test
    public void testGetClient_DataNotFound() {}
}
