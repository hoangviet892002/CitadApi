package HDBanktraining.CitadApi.utils;

import HDBanktraining.CitadApi.dtos.response.CitadReponse;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private static final Logger logger = Logger.getLogger(ExcelReader.class);
    public List<CitadReponse> readCitadFromExcel(String filePath) throws IOException {

        List<CitadReponse> citadReponses = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            CitadReponse citadCodeDTO = new CitadReponse();
            citadCodeDTO.setCode(getCellValue(row.getCell(0)));
            citadCodeDTO.setName(getCellValue(row.getCell(1)));
            citadCodeDTO.setBranch(getCellValue(row.getCell(2)));
            citadReponses.add(citadCodeDTO);
        }

        workbook.close();
        fileInputStream.close();

        return citadReponses;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.format("%.0f", cell.getNumericCellValue());
            default:
                return "";
        }
    }
}
