package com.crawler.service;

import com.crawler.model.NewsEntry;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrawlerServiceImp implements CrawlerService {

    private static final String URL = "https://news.ycombinator.com/";
    private static final Integer MAX_ENTRIES = 30;

    @Override
    public List<NewsEntry> fetchNewsEntries() {
        return fetchEntries();
    }

    private List<NewsEntry> fetchEntries() {
        List<NewsEntry> entries = new ArrayList<>();
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get(URL);
        List<WebElement> items = driver.findElements(By.cssSelector("tr.athing"));
        List<WebElement> subtexts = driver.findElements(By.cssSelector("td.subtext"));
        for (int i = 0; i < MAX_ENTRIES; i++) {
            WebElement item = items.get(i);
            int number = Integer.parseInt(item.findElement(By.cssSelector(".rank")).getText().replace(".", ""));
            String title = item.findElement(By.cssSelector(".titleline a")).getText();
            WebElement subtext = subtexts.get(i);
            int points = 0;
            if (!subtext.findElements(By.className("score")).isEmpty()) {
                points = Integer.parseInt(subtext.findElement(By.className("score")).getText().split(" ")[0]);
            }
            List<WebElement> subtextLinks = subtext.findElements(By.tagName("a"));
            int comments = 0;
            if (!subtextLinks.isEmpty()) {
                String commentText = subtextLinks.get(subtextLinks.size() - 1).getText();
                if (commentText.contains("comment")) {
                    comments = Integer.parseInt(commentText.split(" ")[0]);
                }
            }
            entries.add(new NewsEntry(number, title, points, comments));
        }
        driver.close();
        driver.quit();
        return entries;
    }

    @Override
    public List<NewsEntry> filterLongTitles() {
        return fetchEntries().stream()
                .filter(entry -> entry.getTitleCount() > 5)
                .sorted(Comparator.comparingInt(NewsEntry::getComments).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsEntry> filterShortTitles() {
        return fetchEntries().stream()
                .filter(entry -> entry.getTitleCount() <= 5)
                .sorted(Comparator.comparingInt(NewsEntry::getPoints).reversed())
                .collect(Collectors.toList());
    }

}
