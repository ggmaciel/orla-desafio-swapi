package com.ggmaciel.orlaapi.domain.film;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/films")
@AllArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public ResponseEntity<List<Film>> findAll(@RequestParam(value = "saga_id", required = false) Integer sagaId) {
        return ResponseEntity.ok(this.filmService.findFilms(sagaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable("id") Integer id, @RequestBody FilmDto filmDto) {
        return ResponseEntity.ok(this.filmService.updateFilm(id, filmDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> showFilm(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.filmService.showFilm(id));
    }
}
