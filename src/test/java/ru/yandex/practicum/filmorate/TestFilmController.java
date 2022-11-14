package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TestFilmController {
    private FilmController filmController;
    private Film film;

    @BeforeEach
    public void beforeEach() {
        filmController = new FilmController();
        film = Film.builder().name("Фильм").description("Верные данные.").releaseDate(LocalDate.now()).duration(90).build();
    }

    @Test
    public void errorValidation() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));

        assertThrows(ValidationException.class, () -> filmController.addFilm(film), "Должно быть возвращено исключение.");
    }

    @Test
    public void notExist() {
        film.setId(1);

        assertThrows(FilmOrUserNotExist.class, () -> filmController.updateFilm(film), "Должно быть возвращено исключение.");
    }

    @Test
    public void rightValidation() {
       assertEquals(filmController.addFilm(film), film, "Добавлен не верный фильм.");
    }
}
