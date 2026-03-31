package com.gybandi.datascraper.scraper;


import com.gybandi.datascraper.jsoup.JsoupFetcher;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TelexDataUrlScraperTest {

    private JsoupFetcher jsoupFetcher;
    private TelexUrlDataScraper scraper;

    @BeforeEach
    void setup() {
        jsoupFetcher = mock(JsoupFetcher.class);
        scraper = new TelexUrlDataScraper(jsoupFetcher);
    }

    @Test
    void shouldExtractFacebookProfileIdsFromAllUrls() throws Exception {
        // given
        String html = """
                    <html>
                        <body>
                            <table>
                                <tr>
                                    <td>
                                        <a href="https://www.facebook.com/profile.php?id=123">A</a>
                                        <a href="https://www.facebook.com/profile.php?id=456">B</a>
                                    </td>
                                </tr>
                            </table>
                        </body>
                    </html>
                """;

        Document doc = Jsoup.parse(html);

        // return same document for both URLs
        when(jsoupFetcher.fetchDocument(anyString())).thenReturn(doc);

        // when
        List<String> result = scraper.scrapeData();

        // then
        assertThat(result)
                .containsExactly(
                        "123",
                        "456"
                );

        // verify both URLs were called
        verify(jsoupFetcher, times(3)).fetchDocument(anyString());
    }

    @Test
    void shouldReturnEmptyListWhenExceptionOccurs() throws Exception {
        // given
        when(jsoupFetcher.fetchDocument(anyString()))
                .thenThrow(new IOException("boom"));

        // when
        List<String> result = scraper.scrapeData();

        // then
        assertThat(result).isEmpty();

        verify(jsoupFetcher, times(3)).fetchDocument(anyString());
    }
}