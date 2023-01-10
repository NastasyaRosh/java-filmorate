package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmDao {
    List<Film> getAllFilms();

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Optional<Film> findFilmById(Integer id);
}
