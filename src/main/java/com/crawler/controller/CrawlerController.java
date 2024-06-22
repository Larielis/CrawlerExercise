package com.crawler.controller;

import com.crawler.model.NewsEntry;
import com.crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CrawlerController {
    @Autowired
    private CrawlerService crawlerService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

}
