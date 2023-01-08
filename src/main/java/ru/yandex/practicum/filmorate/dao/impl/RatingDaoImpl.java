package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;
import java.util.Optional;

@Repository
public class RatingDaoImpl implements RatingDao {
    private final JdbcTemplate jdbcTemplate;

    public RatingDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Rating> allRatings() {
        return null;
    }

    @Override
    public Optional<Rating> ratingById(Integer id) {
        return Optional.empty();
    }
}
