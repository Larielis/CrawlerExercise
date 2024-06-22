package com.crawler.service;
import com.crawler.model.NewsEntry;

import java.util.List;

public interface CrawlerService {
    List<NewsEntry> fetchNewsEntries();
    List<NewsEntry> filterLongTitles(List<NewsEntry> entries);
    List<NewsEntry> filterShortTitles(List<NewsEntry> entries);
}
