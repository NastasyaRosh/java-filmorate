package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface FilmGenreDao {
    void addFilmGenre(Integer idFilm, Integer idGenre);
    void deleteFilmGenre(Integer idFilm);

    List<Genre> findGenresByFilmId (Integer id);
}
