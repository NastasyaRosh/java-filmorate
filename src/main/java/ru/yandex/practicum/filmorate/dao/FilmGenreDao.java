package ru.yandex.practicum.filmorate.dao;

public interface FilmGenreDao {
    void addFilmGenre(Integer idFilm, Integer idGenre);
    void deleteFilmGenre(Integer idFilm, Integer idGenre);
}
