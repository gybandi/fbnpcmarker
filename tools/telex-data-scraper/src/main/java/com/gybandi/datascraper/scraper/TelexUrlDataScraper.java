package com.gybandi.datascraper.scraper;

import com.gybandi.datascraper.jsoup.JsoupFetcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TelexUrlDataScraper implements DataScraper {
    private static final String[] TELEX_DATASOURCE_URLS = {
            "https://telex.hu/techtud/2026/03/09/mutatjuk-a-fideszes-kamuprofil-halozat-mind-az-1198-tagjat",
            "https://telex.hu/techtud/2026/03/19/mutatjuk-a-fideszes-politikusok-marcius-15-i-ukran-zaszlos-posztjait-lajkokkal-kihangosito-1954-kamuprofilt"
    };
    private static final String FACEBOOK_URL_BASE = "https://www.facebook.com/profile.php?id=";
    private static final Logger LOGGER = LoggerFactory.getLogger(TelexUrlDataScraper.class);

    private final JsoupFetcher jsoupFetcher;

    public TelexUrlDataScraper(JsoupFetcher jsoupFetcher) {
        this.jsoupFetcher = jsoupFetcher;
    }

    public List<String> scrapeData() {
        Set<String> result = new HashSet<>();
        for (String url : TELEX_DATASOURCE_URLS) {
            result.addAll(scrapeData(url));
        }
        return result.stream().sorted().toList();
    }

    private List<String> scrapeData(String url) {
        List<String> result = new ArrayList<>();
        try {
            // Fetch the page and parse it
            Document doc = jsoupFetcher.fetchDocument(url);

            // Select all <a> elements within the table
            Elements links = doc.select("table a[href*='facebook.com/profile.php?id=']");

            // Iterate through the links and extract the profile URLs
            for (Element link : links) {
                String profileUrl = link.attr("href");

                result.add(profileUrl.replace(FACEBOOK_URL_BASE, ""));
            }
        } catch (IOException e) {
            LOGGER.error("Error while scraping the webpage: {}", e.getMessage());
        }
        return result;
    }
}
