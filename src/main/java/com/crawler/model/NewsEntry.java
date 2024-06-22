    package com.crawler.model;

    import lombok.*;

    @AllArgsConstructor
    @Getter
    @Setter
    public class NewsEntry {
        private int number;
        private String title;
        private int points;
        private int comments;

        public int getTitleCount(){
            return title.split("\\s+").length;
        }
    }
