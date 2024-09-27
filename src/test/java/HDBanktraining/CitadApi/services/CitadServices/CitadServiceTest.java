package HDBanktraining.CitadApi.services.CitadServices;

import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import HDBanktraining.CitadApi.entities.CitadEntity;
import HDBanktraining.CitadApi.mappers.CitadMappers;
import HDBanktraining.CitadApi.repository.CitadRepo.CitadRepo;
import HDBanktraining.CitadApi.services.CitadServices.impl.CitadServiceImpl;
import HDBanktraining.CitadApi.services.SftpServices.SftpService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import HDBanktraining.CitadApi.utils.ExcelReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @MockBean
    private CitadMappers citadMappers;

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
        when(sftpService.downloadFile(anyString(), anyString())).thenReturn(Mono.empty());
    }

    @Test
    public void testProcessCitadEntities_Success() {
        // Arrange
        List<CitadReponse> citadResponses = List.of(new CitadReponse("123", "Branch A", "Name A"));
        Set<String> newFileCodes = citadResponses.stream()
                .map(CitadReponse::getCode)
                .collect(Collectors.toSet());
        CitadEntity existingEntity = new CitadEntity("123", "Branch A", "Old Name");

        when(citadRepo.findByCode("123")).thenReturn(existingEntity);

        // Act & Assert
        citadDataService.processCitadEntities(citadResponses, newFileCodes).subscribe();

        verify(citadRepo, times(1)).save(existingEntity);
    }

    @Test
    public void testCheckAndSaveCitadData_Success() throws Exception {
        // Arrange
        Path tempFilePath = Paths.get("src/main/resources/bank-code-list.xlsx");
        if (Files.exists(tempFilePath)) {
            Files.delete(tempFilePath);
        }
        Files.createFile(tempFilePath);

        List<CitadReponse> citadResponses = List.of(new CitadReponse("123", "Branch A", "Name A"));

        when(sftpService.downloadFile(anyString(), anyString())).thenReturn(Mono.empty());

        when(excelReader.readCitadFromExcel(anyString())).thenReturn(citadResponses);

        // Act
        citadDataService.checkAndSaveCitadData().subscribe();

        // Assert
        verify(sftpService, times(1)).downloadFile(anyString(), anyString());
        verify(citadRepo, times(1)).save(any(CitadEntity.class));
    }

    @Test
    public void testQueryCitads_Success() {
        // Arrange
        String page = "0";
        String size = "10";
        List<CitadEntity> citadEntities = List.of(new CitadEntity("123", "Branch A", "Name A"));
        Page<CitadEntity> pageResult = new PageImpl<>(citadEntities);
        when(citadRepo.findAll(PageRequest.of(0, 10))).thenReturn(pageResult);
        when(citadMappers.entityToCitadReponse(anyList())).thenReturn(List.of(new CitadReponse("123", "Branch A", "Name A")));

        // Act & Assert
        citadDataService.queryCitads(page, size).subscribe(response -> {
            assertEquals(ResponseEnum.SUCCESS.getResponseCode(), response.getResponseCode());
            assertEquals(1, response.getData().getTotalRecord());
            assertEquals(1, response.getData().getData().size());
        });

        verify(citadRepo, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void testValidateRequest_Success() {
        // Act & Assert
        citadDataService.validateRequest("1", "10").subscribe(response -> {
            assertNull(response);
        });
    }

    @Test
    void testValidateRequest_BadRequest() {
        // Act & Assert
        citadDataService.validateRequest(null, "10").subscribe(response -> {
            assertEquals(ResponseEnum.BAD_REQUEST.getResponseCode(), response.getResponseCode());
            assertEquals(ResponseEnum.BAD_REQUEST.getMessage(), response.getMessage());
        });

        citadDataService.validateRequest("abc", "10").subscribe(response -> {
            assertEquals(ResponseEnum.BAD_REQUEST.getResponseCode(), response.getResponseCode());
            assertEquals(ResponseEnum.BAD_REQUEST.getMessage(), response.getMessage());
        });
    }

    @Test
    public void testQueryCitad_Success() {
        // Arrange
        String citadCode = "123";
        CitadEntity citadEntity = new CitadEntity(citadCode, "Branch A", "Name A");
        when(citadRepo.findByCode(citadCode)).thenReturn(citadEntity);

        // Act & Assert
        citadDataService.queryCitad(citadCode).subscribe(result -> {
            assertEquals(citadCode, result.getCode());
            assertEquals("Branch A", result.getBranch());
            assertEquals("Name A", result.getName());
        });

        verify(citadRepo, times(1)).findByCode(citadCode);
    }

    @Test
    public void testInsertCitadData_Success() throws IOException {
        // Arrange
        CitadEntity citadEntity = new CitadEntity("123", "Branch A", "Name A");
        when(citadRepo.findByCode(citadEntity.getCode())).thenReturn(null);

        // Act & Assert
        citadDataService.insertCitadData(citadEntity).subscribe();

        verify(citadRepo, times(1)).save(citadEntity);
    }

    @Test
    public void testInsertCitadData_AlreadyExists() throws IOException {
        // Arrange
        CitadEntity existingCitad = new CitadEntity("123", "Branch A", "Name A");
        when(citadRepo.findByCode(existingCitad.getCode())).thenReturn(existingCitad);

        // Act & Assert
        citadDataService.insertCitadData(existingCitad).subscribe();

        verify(citadRepo, never()).save(any(CitadEntity.class));
    }

}
