package com.gybandi.datascraper.runner;

import com.gybandi.datascraper.model.ScrapedData;
import com.gybandi.datascraper.output.OutputWriter;
import com.gybandi.datascraper.scraper.DataScraper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DataScraperRunnerTest {

    private DataScraper scraper1;
    private DataScraper scraper2;
    private OutputWriter outputWriter;

    private DataScraperRunner runner;

    @BeforeEach
    void setup() {
        scraper1 = mock(DataScraper.class);
        scraper2 = mock(DataScraper.class);
        outputWriter = mock(OutputWriter.class);

        runner = new DataScraperRunner(List.of(scraper1, scraper2), outputWriter);
    }

    @Test
    void shouldAggregateDistinctScrapedDataAndWriteOutput() {
        // given
        when(scraper1.scrapeData()).thenReturn(List.of("1", "2"));
        when(scraper2.scrapeData()).thenReturn(List.of("2", "3"));

        // when
        runner.runScraper();

        // then
        ArgumentCaptor<List<ScrapedData>> captor = ArgumentCaptor.forClass(List.class);
        verify(outputWriter).writeToOutput(captor.capture());

        List<ScrapedData> result = captor.getValue();

        assertThat(result)
                .extracting(ScrapedData::getProfileUri)
                .containsExactlyInAnyOrder("1", "2", "3");
    }
}