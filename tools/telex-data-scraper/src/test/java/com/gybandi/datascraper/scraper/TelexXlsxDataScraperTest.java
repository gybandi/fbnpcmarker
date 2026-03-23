package com.gybandi.datascraper.scraper;

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
        TelexXlsxDataScraper scraper = new TelexXlsxDataScraper(resource);

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
