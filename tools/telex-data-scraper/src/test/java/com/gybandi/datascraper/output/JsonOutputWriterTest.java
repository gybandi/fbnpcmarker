package com.gybandi.datascraper.output;

import com.gybandi.datascraper.model.ScrapedData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JsonOutputWriterTest {

    private Resource resource;
    private JsonOutputWriter writer;

    private File tempFile;

    @BeforeEach
    void setup() throws Exception {
        resource = mock(Resource.class);

        tempFile = File.createTempFile("test-output", ".json");
        tempFile.deleteOnExit();

        when(resource.getFile()).thenReturn(tempFile);

        writer = new JsonOutputWriter(resource);
    }

    @Test
    void shouldWriteJsonToFile() throws Exception {
        // given
        List<ScrapedData> data = List.of(
                new ScrapedData("123"),
                new ScrapedData("456")
        );

        // when
        writer.writeToOutput(data);

        // then
        String content = Files.readString(tempFile.toPath());

        assertThat(content).contains("123");
        assertThat(content).contains("456");
    }

    @Test
    void shouldThrowIllegalStateExceptionWhenIOExceptionOccurs() throws Exception {
        // given
        when(resource.getFile()).thenThrow(new java.io.IOException("boom"));

        writer = new JsonOutputWriter(resource);

        // when / then
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class,
                () -> writer.writeToOutput(List.of())
        );
    }
}