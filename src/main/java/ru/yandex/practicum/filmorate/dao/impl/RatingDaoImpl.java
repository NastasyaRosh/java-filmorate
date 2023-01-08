package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        final String sql = "SELECT * FROM RATING";
        return jdbcTemplate.query(sql, (rs, rowNum) -> ratingMapper(rs));
    }

    @Override
    public Optional<Rating> ratingById(Integer id) {
        String sql = "SELECT * FROM RATING WHERE RATING_ID = ?";
        final List<Rating> ratings = jdbcTemplate.query(sql, (rs, rowNum) -> ratingMapper(rs), id);
        return ratings.size() > 0 ? Optional.of(ratings.get(0)) : Optional.empty();
    }

    private Rating ratingMapper(ResultSet resultSet) throws SQLException {
        Rating ratingOut = new Rating();
        ratingOut.setId(resultSet.getInt("RATING_ID"));
        ratingOut.setName(resultSet.getString("RATING_NAME"));
        ratingOut.setDescription(resultSet.getString("RATING_DESCRIPTION"));
        return ratingOut;
    }
}
