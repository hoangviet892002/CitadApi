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
import reactor.test.StepVerifier;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static org.mockito.Mockito.*;

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
    void setUp() {
        when(defaultSftpSessionFactory.getSession()).thenReturn(session);
    }

    @Test
    void testDownloadFile_Success() throws Exception {
        // Arrange
        String remoteFilePath = "/remote/test.txt";
        String localFilePath = "local/test.txt";

        File localDir = new File("local");
        if (!localDir.exists()) {
            localDir.mkdirs();
        }

        byte[] fileContent = "This is a test file".getBytes();
        InputStream inputStream = new ByteArrayInputStream(fileContent);

        when(session.readRaw(remoteFilePath)).thenReturn(inputStream);

        // Act & Assert
        StepVerifier.create(sftpService.downloadFile(remoteFilePath, localFilePath))
                .expectComplete()
                .verify();

        verify(session, times(1)).readRaw(remoteFilePath);
        verify(session, times(1)).close();
    }

    @Test
    void testDownloadFile_Exception() throws Exception {
        // Arrange
        String remoteFilePath = "/remote/test.txt";
        String localFilePath = "local/test.txt";

        when(session.readRaw(remoteFilePath)).thenThrow(new RuntimeException("SFTP error"));

        // Act & Assert
        StepVerifier.create(sftpService.downloadFile(remoteFilePath, localFilePath))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("File download failed"))
                .verify();

        verify(session, times(1)).readRaw(remoteFilePath);
        verify(session, times(1)).close();
    }

}
