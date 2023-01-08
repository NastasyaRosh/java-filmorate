package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeDao {
    List<Film> findAllPopular(Integer count);

    void addLike(Integer filmId, Integer userId);

    void deleteLike(Integer filmId, Integer userId);
}
