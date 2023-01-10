package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.LikeDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeDaoImpl implements LikeDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Film> findAllPopular(Integer count) {
        String sql = "SELECT FILMS.*, RATING.* FROM FILMS LEFT JOIN LIKES ON FILMS.FILM_ID = LIKES.FILM_ID"
                + " LEFT JOIN RATING ON FILMS.RATING_ID = RATING.RATING_ID"
                + " GROUP BY FILMS.FILM_ID ORDER BY COUNT (LIKES.USER_ID) DESC LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> filmMapper(rs), count);
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        String sql = "INSERT INTO LIKES VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        String sql = "DELETE FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?";
        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public List<User> findUsersByLikesFilm(Integer filmId) {
        String sql = "SELECT USERS.* FROM LIKES LEFT JOIN USERS ON LIKES.USER_ID = USERS.USER_ID WHERE LIKES.FILM_ID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> userMapper(rs), filmId);
    }

    private User userMapper(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("USER_ID"))
                .email(resultSet.getString("EMAIL"))
                .login(resultSet.getString("LOGIN"))
                .name(resultSet.getString("USER_NAME"))
                .birthday(resultSet.getDate("BIRTHDAY").toLocalDate())
                .build();
    }

    private Film filmMapper(ResultSet resultSet) throws SQLException {
        Rating rating = Rating.builder()
                .id(resultSet.getInt("RATING_ID"))
                .name(resultSet.getString("RATING_NAME"))
                .build();
        return Film.builder()
                .id(resultSet.getInt("FILM_ID"))
                .name(resultSet.getString("FILM_NAME"))
                .description(resultSet.getString("FILM_DESCRIPTION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getInt("DURATION"))
                .mpa(rating)
                .genres(Collections.emptyList())
                .build();
    }
}
