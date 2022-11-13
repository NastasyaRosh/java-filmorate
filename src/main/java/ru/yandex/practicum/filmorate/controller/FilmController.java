package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    final LocalDate firstFilm = LocalDate.of(1895, 12, 28);
    private HashMap<Integer, Film> films = new HashMap<>();

    private int id = 0;

    private int setId(Film film) {
        film.setId(++this.id);
        return this.id;
    }

    @GetMapping
    public Collection<Film> allFilms() {
        log.debug("Количество фильмов: " + films.size());
        return films.values();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
            if (film.getReleaseDate().isBefore(firstFilm)) {
                log.warn("Введенный фильм не прошел валидацию.");
                throw new ValidationException("Неверная дата создания фильма.");
            } else {
                films.put(setId(film), film);
                log.debug("Создан новый фильм с ID:" + film.getId());
            }
        return films.get(film.getId());
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws FilmOrUserNotExist {
            if (film.getReleaseDate().isBefore(firstFilm)) {
                log.warn("Введенный фильм не прошел валидацию.");
                throw new ValidationException("Неверная дата создания фильма.");
            } else if (!(films.containsKey(film.getId()))) {
                log.warn("Введен неверный ID.");
                throw new FilmOrUserNotExist("Фильма с таким ID не существует.");
            } else {
            films.replace(film.getId(), film);
            log.debug(String.format("Фильм с ID = %d обновлен.", film.getId()));
            }
        return films.get(film.getId());
    }

}