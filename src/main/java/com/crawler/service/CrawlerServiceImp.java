package com.crawler.service;

import com.crawler.model.NewsEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrawlerServiceImp implements CrawlerService {

    private static final String URL = "https://news.ycombinator.com/";

    @Override
    public List<NewsEntry> fetchNewsEntries() {
        return null;
    }

    @Override
    public List<NewsEntry> filterLongTitles() {
        return List.of();
    }

    @Override
    public List<NewsEntry> filterShortTitles() {
        return List.of();
    }

}
