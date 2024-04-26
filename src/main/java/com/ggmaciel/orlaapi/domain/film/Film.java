package com.ggmaciel.orlaapi.domain.film;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Film {
    private String title;
    private String openingCrawl;
    private Integer episodeId;
    private String director;
    private String producer;
    private String releaseDate;
    private Integer version;
    private LocalDateTime updatedAt;

    public Film() {}

    public Film(
            String title,
            String openingCrawl,
            Integer episodeId,
            String director,
            String producer,
            String releaseDate) {
        this.title = title;
        this.openingCrawl = openingCrawl;
        this.episodeId = episodeId;
        this.director = director;
        this.producer = producer;
        this.releaseDate = releaseDate;
        this.version = 1;
    }
}
