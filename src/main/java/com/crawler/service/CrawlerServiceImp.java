package com.crawler.service;

import com.crawler.model.NewsEntry;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrawlerServiceImp implements CrawlerService {

    private static final String URL = "https://news.ycombinator.com/";
    private static final Integer MAX_ENTRIES = 30;

    @Override
    public List<NewsEntry> fetchNewsEntries() {
        return fetchEntries();
    }

    public List<NewsEntry> fetchEntries() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get(URL);
        List<WebElement> items = driver.findElements(By.cssSelector("tr.athing"));
        List<WebElement> subtexts = driver.findElements(By.cssSelector("td.subtext"));

        List<NewsEntry> entries = new ArrayList<>();

        for (int i = 0; i < Math.min(items.size(), MAX_ENTRIES); i++) {
            entries.add(createNewsEntry(items.get(i), subtexts.get(i)));
        }

        driver.quit();
        return entries;
    }

    private NewsEntry createNewsEntry(WebElement item, WebElement subtext) {
        int number = extractNumber(item);
        String title = extractTitle(item);
        int points = extractPoints(subtext);
        int comments = extractComments(subtext);
        return new NewsEntry(number, title, points, comments);
    }

    private int extractNumber(WebElement item) {
        return Integer.parseInt(item.findElement(By.cssSelector(".rank")).getText().replace(".", ""));
    }

    private String extractTitle(WebElement item) {
        return item.findElement(By.cssSelector(".titleline a")).getText();
    }

    private int extractPoints(WebElement subtext) {
        int points = 0;
        if (!subtext.findElements(By.className("score")).isEmpty()) {
            points = Integer.parseInt(subtext.findElement(By.className("score")).getText().split(" ")[0]);
        }
        return points;
    }

    private int extractComments(WebElement subtext) {
        List<WebElement> subtextLinks = subtext.findElements(By.tagName("a"));
        int comments = 0;
        if (!subtextLinks.isEmpty()) {
            String commentText = subtextLinks.get(subtextLinks.size() - 1).getText();
            if (commentText.contains("comment") || commentText.contains("comments")) {
                comments = Integer.parseInt(commentText.split(" ")[0]);
            }
        }
        return comments;
    }

    @Override
    public List<NewsEntry> filterLongTitles() {
        List<NewsEntry> longTitlesEntries = new ArrayList<>();
        for (NewsEntry entry : fetchEntries()) {
            if (entry.getTitleCount() > 5) {
                longTitlesEntries.add(entry);
            }
        }
        longTitlesEntries.sort((e1, e2) -> Integer.compare(e2.getComments(), e1.getComments()));
        return longTitlesEntries;
    }

    @Override
    public List<NewsEntry> filterShortTitles() {
        List<NewsEntry> shortTitlesEntries = new ArrayList<>();
        for (NewsEntry entry : fetchEntries()) {
            if (entry.getTitleCount() <= 5) {
                shortTitlesEntries.add(entry);
            }
        }
        shortTitlesEntries.sort((e1, e2) -> Integer.compare(e2.getPoints(), e1.getPoints()));
        return shortTitlesEntries;
    }
}
