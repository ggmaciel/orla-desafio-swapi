package com.ggmaciel.orlaapi.domain.film;

import com.ggmaciel.orlaapi.data.FilmDataLoader;
import com.ggmaciel.orlaapi.exceptions.NotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class FilmService {

    private final FilmDataLoader filmDataLoader;

    public List<Film> findFilms(Integer sagaId) {
        if (sagaId == null) {
            return new ArrayList<>(filmDataLoader.getFilms().values());
        }
        return filmDataLoader.getFilms().values().stream()
                .filter(film -> film.getEpisodeId().equals(sagaId))
                .toList();
    }

    public Film updateFilm(Integer id, FilmDto filmDto) {
        var filmToUpdate = loadFilm(id);
        filmToUpdate.setOpeningCrawl(filmDto.getOpeningCrawl());
        filmToUpdate.setVersion(filmToUpdate.getVersion() + 1);
        filmToUpdate.setUpdatedAt(LocalDateTime.now());

        var filmUpdated = filmDataLoader.getFilms().put(id, filmToUpdate);
        log.info("Film updated");

        return filmUpdated;
    }

    private Film loadFilm(Integer id) {
        var film = filmDataLoader.getFilms().get(id);
        if (film == null) {
            throw new NotFoundException("Film not found");
        }
        return film;
    }

    public Film showFilm(Integer id) {
        return loadFilm(id);
    }
}
