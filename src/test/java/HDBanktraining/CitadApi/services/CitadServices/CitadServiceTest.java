package HDBanktraining.CitadApi.services.CitadServices;

import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import HDBanktraining.CitadApi.entities.CitadEntity;
import HDBanktraining.CitadApi.repository.CitadRepo.CitadRepo;
import HDBanktraining.CitadApi.services.CitadServices.impl.CitadServiceImpl;
import HDBanktraining.CitadApi.services.SftpServices.SftpService;
import HDBanktraining.CitadApi.utils.ExcelReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "HOST=123",
        "USERNAME=test",
        "PASSWORD=testpassword"
})
public class CitadServiceTest {

    @MockBean
    private CitadRepo citadRepo;

    @Autowired
    private CitadServiceImpl citadDataService;

    @MockBean
    private SftpService sftpService;

    @MockBean
    private ExcelReader excelReader;

    @BeforeEach
    void setUp() {
        // Mock data for citad entity
        CitadEntity existingEntity = new CitadEntity("12345", "Bank Name", "Branch Name");

        // Mock data for citad response
        CitadReponse mockCitadResponse = new CitadReponse("12345", "Bank Name", "Branch Name");

        // Mock datalisist for citad response
        List<CitadReponse> mockCitadResponses = new ArrayList<>();
        mockCitadResponses.add(mockCitadResponse);

        // Config mock for citad repo
        when(citadRepo.findByCode("12345")).thenReturn(existingEntity);

        // Config mock for excel reader
        try {
            when(excelReader.readCitadFromExcel(anyString())).thenReturn(mockCitadResponses);
        } catch (IOException e) {
            fail("Failed to set up ExcelReader mock", e);
        }

        // Config mock for sftp service
        doNothing().when(sftpService).downloadFile(anyString(), anyString());
    }

    @Test
    public void testCheckAndSaveCitadData_Success() throws IOException {}

    @Test
    public void testProcessCitadEntities_Success() {}

    @Test
    public void testCheckAndSaveCitadData() throws Exception {
        String localFilePath = "/local/file.xlsx";

        doNothing().when(sftpService).downloadFile(anyString(), anyString());

        List<CitadReponse> mockCitadResponses = new ArrayList<>();
        mockCitadResponses.add(new CitadReponse("12345", "Bank Name", "Branch Name"));
        when(excelReader.readCitadFromExcel(anyString())).thenReturn(mockCitadResponses);

        CitadEntity existingEntity = new CitadEntity("12345", "Old Bank Name", "Old Branch Name");
        when(citadRepo.findByCode("12345")).thenReturn(existingEntity);

        citadDataService.checkAndSaveCitadData().block();

        verify(sftpService, times(1)).downloadFile(anyString(), anyString());

        CitadEntity updatedEntity = new CitadEntity("12345", "Bank Name", "Branch Name");
        verify(citadRepo, times(1)).save(argThat(entity ->
                entity.getCode().equals(updatedEntity.getCode()) &&
                        entity.getName().equals(updatedEntity.getName()) &&
                        entity.getBranch().equals(updatedEntity.getBranch())
        ));
    }

    @Test
    public void testQueryCitads_Success() {}

    @Test
    public void testQueryCitads_InvalidRequest() {}

    @Test
    public void testValidateRequest_InvalidPageSize() {}

    @Test
    public void testQueryCitad_Success() {}

    @Test
    public void testInsertCitadData_Success() throws IOException {}

    @Test
    public void testInsertCitadData_AlreadyExists() throws IOException {}

}
