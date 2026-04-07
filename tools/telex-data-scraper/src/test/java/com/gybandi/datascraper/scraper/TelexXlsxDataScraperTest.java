package com.gybandi.datascraper.scraper;

import com.gybandi.datascraper.properties.XlsxScraperProperties;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TelexXlsxDataScraperTest {

    @Test
    void shouldExtractFacebookIdsFromXlsx() {
        // GIVEN
        Resource resource = new ClassPathResource("testinput.xlsx");
        XlsxScraperProperties xlsxScraperProperties = new XlsxScraperProperties();
        xlsxScraperProperties.setFiles(List.of(resource));
        TelexXlsxDataScraper scraper = new TelexXlsxDataScraper(xlsxScraperProperties);

        // WHEN
        List<String> result = scraper.scrapeData();

        // THEN
        assertThat(result)
                .containsAll(List.of(
                        "1",
                        "2",
                        "some.guy"
                ));
    }
}
