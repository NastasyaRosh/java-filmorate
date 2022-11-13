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

    @BeforeEach
    public void beforeEach() {
        filmController = new FilmController();
    }

    @Test
    public void errorValidation() {
        Film withFalseDate = Film.builder().name("Фильм").description("Ложная дата.").releaseDate(LocalDate.of(1895, 12, 27)).duration(90).build();

        assertThrows(ValidationException.class, () -> filmController.addFilm(withFalseDate), "Должно быть возвращено исключение.");
    }

    @Test
    public void notExist() {
        Film withFalseID = Film.builder().id(1).name("Фильм").description("Несуществующее ID.").releaseDate(LocalDate.now()).duration(90).build();

        assertThrows(FilmOrUserNotExist.class, () -> filmController.updateFilm(withFalseID), "Должно быть возвращено исключение.");
    }

    @Test
    public void rightValidation() {
        Film requestFilm = Film.builder().name("Фильм").description("Верные данные.").releaseDate(LocalDate.now()).duration(90).build();
        Film responseFilm = filmController.addFilm(requestFilm);

        assertEquals(responseFilm, requestFilm, "Добавлен не верный фильм.");
    }
}
