package HDBanktraining.CitadApi.services.SftpServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "HOST=123",
        "USERNAME=test",
        "PASSWORD=testpassword"
})
public class SftpServiceTest {

    @Autowired
    private SftpService sftpService;

    @MockBean
    private DefaultSftpSessionFactory defaultSftpSessionFactory;

    @MockBean
    private SftpSession session;

    @BeforeEach
    void setUp() {}

    @Test
    void testDownloadFile_Success() throws Exception {}

    @Test
    void testDownloadFile_Exception() throws Exception {}

}
