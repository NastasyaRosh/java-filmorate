package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingDao {
    List<Rating> getAllRatings();

    Optional<Rating> findRatingById(Integer id);
}
