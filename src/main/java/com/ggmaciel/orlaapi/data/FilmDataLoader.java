package com.ggmaciel.orlaapi.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggmaciel.orlaapi.config.AppProperties;
import com.ggmaciel.orlaapi.config.Resilience4JConfig;
import com.ggmaciel.orlaapi.domain.film.Film;
import com.ggmaciel.orlaapi.exceptions.ApiException;
import com.ggmaciel.orlaapi.external.SwapiFilm;
import com.ggmaciel.orlaapi.external.SwapiResponse;
import io.github.resilience4j.retry.Retry;
import io.vavr.control.Try;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class FilmDataLoader {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final AppProperties appProperties;
    private final Resilience4JConfig resilience;

    private Map<Integer, Film> films;

    public void loadFilms() {
        log.info("Start request to: {} ", appProperties.getSwapiUrl());

        var response = Retry.decorateSupplier(
                resilience.getRetry("filmsApi", 2, 5),
                () -> restTemplate.getForEntity(appProperties.getSwapiUrl(), SwapiResponse.class));

        var responseFromSwapi = Try.ofSupplier(response).getOrElseThrow(() -> {
            log.error("error to call api: {}", appProperties.getSwapiUrl());
            throw new ApiException(String.format("error to call api: %s", appProperties.getSwapiUrl()));
        });

        log.info("Request done");
        convertFromSwapiFilmListToFilmMap(responseFromSwapi.getBody().getResults());
        log.info("load films completed");
    }

    private void convertFromSwapiFilmListToFilmMap(List<SwapiFilm> swapiFilms) {
        this.films = swapiFilms.stream()
                .map(swapiFilm -> new Film(
                        swapiFilm.getTitle(),
                        swapiFilm.getOpeningCrawl(),
                        swapiFilm.getEpisodeId(),
                        swapiFilm.getDirector(),
                        swapiFilm.getProducer(),
                        swapiFilm.getReleaseDate()))
                .collect(Collectors.toMap(Film::getEpisodeId, film -> film));
    }

    public Map<Integer, Film> getFilms() {
        if (films == null) {
            loadFilms();
        }
        return films;
    }
}
