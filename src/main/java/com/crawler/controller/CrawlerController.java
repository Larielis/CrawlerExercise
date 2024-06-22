package com.crawler.controller;

import com.crawler.model.NewsEntry;
import com.crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CrawlerController {
    @Autowired
    private CrawlerService crawlerService;

    @GetMapping("/")
    public ModelAndView home() {
      return createModelAndView("home", crawlerService.fetchNewsEntries());
    }

    @GetMapping("/longestTitles")
    public ModelAndView longestTitles() {
        return createModelAndView("longestTitles", crawlerService.filterLongTitles());
    }

    @GetMapping("/shortestTitles")
    public ModelAndView shortestTitles() {
        return createModelAndView("shortestTitles", crawlerService.filterShortTitles());
    }

    private ModelAndView createModelAndView(String viewName, List<NewsEntry> entries) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("entries", entries);
        return modelAndView;
    }
}
