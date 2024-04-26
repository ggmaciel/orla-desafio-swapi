package com.ggmaciel.orlaapi.domain.film;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ggmaciel.orlaapi.data.FilmDataLoader;
import com.ggmaciel.orlaapi.exceptions.NotFoundException;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @InjectMocks
    private FilmService filmService;

    @Mock
    private FilmDataLoader filmDataLoader;

    @BeforeEach
    public void setUp() {
        var film = new Film();
        film.setTitle("Title test");
        film.setEpisodeId(4);
        film.setVersion(1);
        film.setOpeningCrawl("first description");

        var filmsMocked = new HashMap<Integer, Film>();
        filmsMocked.put(4, film);

        Mockito.when(filmDataLoader.getFilms()).thenReturn(filmsMocked);
    }

    @DisplayName(
            """
			given a film id and a new description
			when update film
			should update just a description
			""")
    @Test
    public void shouldUpdateFilm() {
        var requestDto = FilmDto.builder().openingCrawl("new description").build();
        assertEquals("first description", filmDataLoader.getFilms().get(4).getOpeningCrawl());
        assertEquals(1, filmDataLoader.getFilms().get(4).getVersion());

        filmService.updateFilm(4, requestDto);

        assertEquals("new description", filmDataLoader.getFilms().get(4).getOpeningCrawl());
        assertEquals(2, filmDataLoader.getFilms().get(4).getVersion());
    }

    @Test
    @DisplayName(
            """
			given a film id incorrect and a new description
			when update film
			should return not found exception
			""")
    public void shouldNotFoundException() {
        var message = Assertions.assertThrows(NotFoundException.class, () -> {
            var requestDto = FilmDto.builder().openingCrawl("new description").build();
            filmService.updateFilm(100, requestDto);
        });
        assertEquals("Film not found", message.getMessage());
    }

    @DisplayName("""
			given a film id
			when show details of film
			should return data of film
			""")
    @Test
    public void shouldShowDetailsFilm() {
        var film = filmService.showFilm(4);

        assertEquals("Title test", film.getTitle());
        assertEquals("first description", film.getOpeningCrawl());
        assertEquals(4, film.getEpisodeId());
    }

    @DisplayName(
            """
			given a film id incorrect
			when show details of film
			should return not found exception
			""")
    @Test
    public void shouldException() {
        var message = Assertions.assertThrows(NotFoundException.class, () -> {
            filmService.showFilm(100);
        });
        assertEquals("Film not found", message.getMessage());
    }
}
