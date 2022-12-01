package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    List<Film> allFilms();

    Film addFilm(Film film);

    Film updateFilm(Film film) throws FilmOrUserNotExist;

    Film filmById(int id) throws FilmOrUserNotExist;
}
