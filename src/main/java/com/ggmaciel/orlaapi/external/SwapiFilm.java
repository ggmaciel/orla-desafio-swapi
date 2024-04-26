package com.ggmaciel.orlaapi.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapiFilm {
    private String title;

    @JsonProperty("opening_crawl")
    private String openingCrawl;

    @JsonProperty("episode_id")
    private Integer episodeId;

    private String director;
    private String producer;

    @JsonProperty("release_date")
    private String releaseDate;
}
