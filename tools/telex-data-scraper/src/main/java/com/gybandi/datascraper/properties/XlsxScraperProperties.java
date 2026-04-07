package com.gybandi.datascraper.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "input.scraper.xlsx")
public class XlsxScraperProperties {

    private List<Resource> files = new ArrayList<>();

    public List<Resource> getFiles() {
        return files;
    }

    public void setFiles(List<Resource> files) {
        this.files = files;
    }
}
