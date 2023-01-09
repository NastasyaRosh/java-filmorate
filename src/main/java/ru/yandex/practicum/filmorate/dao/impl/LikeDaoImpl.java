package ru.yandex.practicum.filmorate.dao.impl;

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
public class LikeDaoImpl implements LikeDao {
    private final JdbcTemplate jdbcTemplate;

    public LikeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
        User userOut = new User();
        userOut.setId(resultSet.getInt("USER_ID"));
        userOut.setEmail(resultSet.getString("EMAIL"));
        userOut.setLogin(resultSet.getString("LOGIN"));
        userOut.setName(resultSet.getString("USER_NAME"));
        userOut.setBirthday(resultSet.getDate("BIRTHDAY").toLocalDate());
        return userOut;
    }

    private Film filmMapper(ResultSet resultSet) throws SQLException{
        Film filmOut = new Film();
        Rating rating = new Rating();
        filmOut.setId(resultSet.getInt("FILM_ID"));
        filmOut.setName(resultSet.getString("FILM_NAME"));
        filmOut.setDescription(resultSet.getString("FILM_DESCRIPTION"));
        filmOut.setReleaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate());
        filmOut.setDuration(resultSet.getInt("DURATION"));
        rating.setId(resultSet.getInt("RATING_ID"));
        rating.setName(resultSet.getString("RATING_NAME"));
        filmOut.setMpa(rating);
        filmOut.setGenres(Collections.emptyList());
        return filmOut;
    }
}
