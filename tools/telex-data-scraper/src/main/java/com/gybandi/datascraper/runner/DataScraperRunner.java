package com.gybandi.datascraper.runner;

import com.gybandi.datascraper.model.ScrapedData;
import com.gybandi.datascraper.output.OutputWriter;
import com.gybandi.datascraper.scraper.DataScraper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataScraperRunner {

    private final List<DataScraper> scrapers;
    private final OutputWriter outputWriter;

    public DataScraperRunner(List<DataScraper> scrapers, OutputWriter outputWriter) {
        this.scrapers = scrapers;
        this.outputWriter = outputWriter;
    }

    @PostConstruct
    public void runScraper() {
        List<ScrapedData> result = scrapers.stream()
                .flatMap(scraper -> scraper.scrapeData().stream())
                .map(profileId -> new ScrapedData(profileId))
                .distinct()
                .toList();
        outputWriter.writeToOutput(result);
    }
}
