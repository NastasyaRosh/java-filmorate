package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

@Service
public class RatingService {
    private final RatingDao ratingDao;

    public RatingService(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    public List<Rating> allRatings() {
        return ratingDao.allRatings();
    }

    public Rating ratingById(Integer id) throws FilmOrUserNotExist {
        Rating ratingOut = ratingDao.ratingById(id).orElseThrow(() ->
                new FilmOrUserNotExist(String.format("Рейтинг с ID %d не найден.", id)));
        return ratingOut;
    }
}
