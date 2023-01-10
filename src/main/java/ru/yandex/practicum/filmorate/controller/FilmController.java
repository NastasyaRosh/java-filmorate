package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;
    private static final int DEFAULT_COUNT = 10;

    @GetMapping
    public List<Film> getAllFilms() {
        log.debug("Количество фильмов: " + filmService.getAllFilms().size());
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.debug("Создание фильма: " + film.getName());
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws FilmOrUserNotExist {
        log.debug(String.format("Обновление фильма с ID = %d.", film.getId()));
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) throws FilmOrUserNotExist {
        return filmService.findFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film setLike(@PathVariable int id, @PathVariable int userId) {
        log.debug(String.format("Фильму с ID = %d ставит лайк пользователь с ID = %d.", id, userId));
        return filmService.setLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.debug(String.format("У фильма с ID = %d снимает свой лайк пользователь с ID = %d.", id, userId));
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
        log.debug(String.format("Вывод %d наиболее популярных фильмов. Всего имеется %d фильмов.",
                count, filmService.getAllFilms().size()));
        if (count == null) {
            count = DEFAULT_COUNT;
        }
        return filmService.findPopularFilms(count);
    }
}