package HDBanktraining.CitadApi.utils;

import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "HOST=103.214.8.153",
        "USERNAME=administrator",
        "PASSWORD=%E-NE1e6WS"
})
public class ExcelReaderTest {

    @InjectMocks
    private ExcelReader excelReader;

    public ExcelReaderTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReadCitadFromExcel(@TempDir Path tempDir) throws IOException {
        // Tạo file Excel mock
        File mockExcelFile = tempDir.resolve("mockfile.xlsx").toFile();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("12345");
            row.createCell(1).setCellValue("Bank Name");
            row.createCell(2).setCellValue("Branch Name");

            try (FileOutputStream fos = new FileOutputStream(mockExcelFile)) {
                workbook.write(fos);
            }
        }

        // Gọi phương thức readCitadFromExcel
        List<CitadReponse> citadResponses = excelReader.readCitadFromExcel(mockExcelFile.getAbsolutePath());

        // Kiểm tra kết quả
        assertNotNull(citadResponses, "Kết quả trả về không được null");
        assertFalse(citadResponses.isEmpty(), "Danh sách trả về không được rỗng");
        assertEquals("12345", citadResponses.get(0).getCode(), "Mã Citad không khớp");
        assertEquals("Bank Name", citadResponses.get(0).getName(), "Tên ngân hàng không khớp");
        assertEquals("Branch Name", citadResponses.get(0).getBranch(), "Tên chi nhánh không khớp");
    }
}
