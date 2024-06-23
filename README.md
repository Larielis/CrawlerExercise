# Hacker News Crawler App

This app is designed to fetch the first 30 news entries from the news website [Hacker News](https://news.ycombinator.com/)

## Features
- **Fetch News Entries** :Retrieves in json format the first 30 news entries
- **Filter Long Titles** :Filter entries that have titles with more than five words, ordering it by the number of comments
- **Filter Short Titles** :Filter entries that have titles with five or fewer words, ordering it by points

## Tech stack
- Java 17
- Spring Boot
- Selenium
- Maven
- JUnit
- Mockito

## Usage

### Endpoints
- **GET /** : Fetch all the first 30 news entries
- **GET /longestTitles** : Fetch entries with titles longer than five words, ordered by the number of comments
- **GET /shortestTitles** : Fetch entries with titles equal or lower than five words, ordered by points

## Sample request
    curl -X GET http://localhost:8080/
## Sample response
    [
    {
        "number": 1,
        "title": "Short title",
        "points": 50,
        "comments": 10
    },
    {
        "number": 2,
        "title": "This is a very long title for testing",
        "points": 78,
        "comments": 34
    }
    ]
