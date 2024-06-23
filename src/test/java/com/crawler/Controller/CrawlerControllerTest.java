package com.crawler.Controller;

import com.crawler.Utils.TestData;
import com.crawler.controller.CrawlerController;
import com.crawler.exceptions.GlobalExceptionHandler;
import com.crawler.model.NewsEntry;
import com.crawler.service.CrawlerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebMvcTest(CrawlerController.class)
public class CrawlerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrawlerService crawlerService;

    @InjectMocks
    private CrawlerController crawlerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(crawlerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAllEntries() throws Exception {
        List<NewsEntry> mockedEntries = TestData.createMockedEntries();
        when(crawlerService.fetchNewsEntries()).thenReturn(mockedEntries);

        String expectedJson = """
            [
                {"number":1,
                  "title":"Short title",
                  "points":50,
                  "comments":10},
                {"number":2,
                  "title":"This is a very long title for testing",
                  "points":78,
                  "comments":34}
            ]
            """;

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testGetAllEntriesNoContent() throws Exception {
        when(crawlerService.fetchNewsEntries()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Resource not found: No news entries found"));
    }

    @Test
    void testGetLongestTitles() throws Exception {
        List<NewsEntry> mockedLongTitles = TestData.createMockedLongTitlesEntries();
        when(crawlerService.filterLongTitles()).thenReturn(mockedLongTitles);

        String expectedJson = """
            [
                {"number":2,
                  "title":"Yet another very long title for testing purposes",
                  "points":88,
                  "comments":45},
                {"number":1,
                  "title":"This is a very long title for testing",
                  "points":78,
                  "comments":34}
            ]
            """;

        mockMvc.perform(get("/longestTitles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testGetLongestTitlesNoContent() throws Exception {
        when(crawlerService.filterLongTitles()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/longestTitles"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Resource not found: No news entries with long titles found"));
    }

    @Test
    void testGetShortestTitles() throws Exception {
        List<NewsEntry> mockedShortTitles = TestData.createMockedShortTitlesEntries();
        when(crawlerService.filterShortTitles()).thenReturn(mockedShortTitles);

        String expectedJson = """
            [
                {"number":2,
                  "title":"Another short",
                  "points":40,
                  "comments":10},
                {"number":1,
                  "title":"Short",
                  "points":20,
                  "comments":5}
            ]
            """;

        mockMvc.perform(get("/shortestTitles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testGetShortestTitlesNoContent() throws Exception {
        when(crawlerService.filterShortTitles()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/shortestTitles"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Resource not found: No news entries with short titles found"));
    }

    @Test
    void testServiceError() throws Exception {
        when(crawlerService.fetchNewsEntries()).thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(get("/"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred: Service error"));
    }
}
