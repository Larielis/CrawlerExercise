package com.crawler.Service;

import com.crawler.Utils.TestData;
import com.crawler.model.NewsEntry;
import com.crawler.service.CrawlerServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CrawlerServiceImpTest {
    @InjectMocks
    private CrawlerServiceImp crawlerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchNewsEntries() {
        List<NewsEntry> mockedEntries = TestData.createMockedEntries();
        CrawlerServiceImp spyService = spy(crawlerService);
        doReturn(mockedEntries).when(spyService).fetchEntries();

        List<NewsEntry> entries = spyService.fetchNewsEntries();

        assertEquals(2, entries.size());
        assertEquals("Short title", entries.get(0).getTitle());
        assertEquals(50, entries.get(0).getPoints());
        assertEquals(10, entries.get(0).getComments());

        assertEquals("This is a very long title for testing", entries.get(1).getTitle());
        assertEquals(78, entries.get(1).getPoints());
        assertEquals(34, entries.get(1).getComments());
    }

    @Test
    void testFilterLongTitles() {
        List<NewsEntry> mockedEntries = TestData.createMockedMixedEntries();
        CrawlerServiceImp spyService = spy(crawlerService);
        doReturn(mockedEntries).when(spyService).fetchEntries();
        spyService.fetchNewsEntries();

        List<NewsEntry> longTitleEntries = spyService.filterLongTitles();

        assertEquals(2, longTitleEntries.size());
        assertEquals("Longest title ever seen in a test case", longTitleEntries.get(0).getTitle());
        assertEquals(50, longTitleEntries.get(0).getComments());

        assertEquals("A sufficiently long title for tests", longTitleEntries.get(1).getTitle());
        assertEquals(20, longTitleEntries.get(1).getComments());
    }

    @Test
    void testFilterShortTitles() {
        List<NewsEntry> mockedEntries = TestData.createMockedMixedEntries();
        CrawlerServiceImp spyService = spy(crawlerService);
        doReturn(mockedEntries).when(spyService).fetchEntries();
        spyService.fetchNewsEntries();

        List<NewsEntry> shortTitleEntries = spyService.filterShortTitles();

        assertEquals(2, shortTitleEntries.size());
        assertEquals("Short test", shortTitleEntries.get(0).getTitle());
        assertEquals(60, shortTitleEntries.get(0).getPoints());

        assertEquals("Short", shortTitleEntries.get(1).getTitle());
        assertEquals(10, shortTitleEntries.get(1).getPoints());
    }
}
