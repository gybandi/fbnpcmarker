package com.gybandi.datascraper.output;

import com.gybandi.datascraper.model.ScrapedData;

import java.util.List;

public interface OutputWriter {

    void writeToOutput(List<ScrapedData> data);

}
