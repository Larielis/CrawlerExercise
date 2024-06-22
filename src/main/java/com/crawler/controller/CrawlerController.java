package com.crawler.controller;

import com.crawler.model.NewsEntry;
import com.crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CrawlerController {
    @Autowired
    private CrawlerService crawlerService;

    @GetMapping("/")
    public List<NewsEntry> getAllEntries() {
        return crawlerService.fetchNewsEntries();
    }

    @GetMapping("/longestTitles")
    public List<NewsEntry> getLongestTitles() {
        return crawlerService.filterLongTitles();
    }

    @GetMapping("/shortestTitles")
    public List<NewsEntry> getShortestTitles() {
        return crawlerService.filterShortTitles();
    }
}
