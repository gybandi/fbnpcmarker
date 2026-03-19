package com.gybandi.datascraper.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsoupFetcher {

    public Document fetchDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

}
