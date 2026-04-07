package com.gybandi.datascraper.scraper;

import com.gybandi.datascraper.properties.XlsxScraperProperties;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TelexXlsxDataScraper implements DataScraper {
    private final XlsxScraperProperties properties;

    private static final String FACEBOOK_URL_BASE = "https://www.facebook.com/";
    private static final String FACEBOOK_ID_PART = "profile.php?id=";

    public TelexXlsxDataScraper(XlsxScraperProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<String> scrapeData() {
        List<String> result = new ArrayList<>();
        for (Resource xlsxFile : properties.getFiles()) {
            try {
                Workbook workbook = new XSSFWorkbook(xlsxFile.getFile());

                Sheet sheet = workbook.getSheetAt(0);

                Map<Integer, List<String>> data = new HashMap<>();
                int i = 0;
                for (Row row : sheet) {
                    data.put(i, new ArrayList<String>());
                    for (Cell cell : row) {
                        String stringCellValue = cell.getStringCellValue();
                        if (stringCellValue == null || stringCellValue.trim().isEmpty()) {
                            continue;
                        }
                        if (stringCellValue.contains(FACEBOOK_URL_BASE)) {
                            String profileId = stringCellValue.replace(FACEBOOK_URL_BASE, "").replace(FACEBOOK_ID_PART, "").trim();
                            result.add(profileId);
                        }

                    }
                    i++;
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            } catch (InvalidFormatException e) {
                throw new IllegalStateException(e);
            }
        }
        return result;
    }
}
