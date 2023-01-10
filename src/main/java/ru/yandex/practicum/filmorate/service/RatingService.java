package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingDao ratingDao;

    public List<Rating> getAllRatings() {
        return ratingDao.getAllRatings();
    }

    public Rating findRatingById(Integer id) throws FilmOrUserNotExist {
        return ratingDao.findRatingById(id).orElseThrow(() ->
                new FilmOrUserNotExist(String.format("Рейтинг с ID %d не найден.", id)));
    }
}
