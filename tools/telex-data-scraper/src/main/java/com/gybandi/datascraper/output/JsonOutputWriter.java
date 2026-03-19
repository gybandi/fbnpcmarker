package com.gybandi.datascraper.output;

import com.gybandi.datascraper.model.ScrapedData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonOutputWriter implements OutputWriter {

    private ObjectMapper objectMapper;
    private final Resource outputPath;

    public JsonOutputWriter(@Value("${output.json.path}") Resource outputPath) {
        this.objectMapper = new ObjectMapper();
        this.outputPath = outputPath;
    }

    public void writeToOutput(List<ScrapedData> data) {
        File outputFile = null;
        try {
            outputFile = outputPath.getFile();
            objectMapper.writeValue(outputFile, data);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
