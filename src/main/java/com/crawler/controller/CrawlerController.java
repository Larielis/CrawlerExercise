package com.crawler.controller;

import com.crawler.exceptions.ResourceNotFoundException;
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
        List<NewsEntry> entries = crawlerService.fetchNewsEntries();
        if (entries.isEmpty()) {
            throw new ResourceNotFoundException("No news entries found");
        }
        return entries;
    }

    @GetMapping("/longestTitles")
    public List<NewsEntry> getLongestTitles() {
        List<NewsEntry> entries = crawlerService.filterLongTitles();
        if (entries.isEmpty()) {
            throw new ResourceNotFoundException("No news entries with long titles found");
        }
        return entries;
    }

    @GetMapping("/shortestTitles")
    public List<NewsEntry> getShortestTitles() {
        List<NewsEntry> entries = crawlerService.filterShortTitles();
        if (entries.isEmpty()) {
            throw new ResourceNotFoundException("No news entries with short titles found");
        }
        return entries;
    }
}
