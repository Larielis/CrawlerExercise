package com.crawler.Utils;

import com.crawler.model.NewsEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData {
    public static List<NewsEntry> createMockedEntries() {
        return Arrays.asList(
                new NewsEntry(1, "Short title", 50, 10),
                new NewsEntry(2, "This is a very long title for testing", 78, 34)
        );
    }

    public static List<NewsEntry> createMockedShortTitlesEntries() {
        List<NewsEntry> entries = new ArrayList<>();
        entries.add(new NewsEntry(1, "Short", 20, 5));
        entries.add(new NewsEntry(2, "Another short", 40, 10));
        return entries;
    }

    public static List<NewsEntry> createMockedLongTitlesEntries() {
        List<NewsEntry> entries = new ArrayList<>();
        entries.add(new NewsEntry(1, "This is a very long title for testing", 78, 34));
        entries.add(new NewsEntry(2, "Yet another very long title for testing purposes", 88, 45));
        return entries;
    }

    public static List<NewsEntry> createMockedMixedEntries() {
        List<NewsEntry> entries = new ArrayList<>();
        entries.add(new NewsEntry(1, "Short", 10, 5));
        entries.add(new NewsEntry(2, "A sufficiently long title for tests", 40, 20));
        entries.add(new NewsEntry(3, "Short test", 60, 15));
        entries.add(new NewsEntry(4, "Longest title ever seen in a test case", 90, 50));
        return entries;
    }
}
