package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> allFilms() {
        final String sql = "SELECT * FROM FILMS" +
                " JOIN RATING ON FILMS.RATING_ID = RATING.RATING_ID" +
                //" JOIN FILM_GENRE ON FILMS.FILM_ID = FILM_GENRE.FILM_ID" +
                " GROUP BY FILMS.FILM_ID";
        return jdbcTemplate.query(sql, (rs, rowNum) -> filmMapper(rs));
    }

    @Override
    public Film addFilm(Film film) {
        String sql = "INSERT INTO FILMS (FILM_NAME, FILM_DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID) VALUES (?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getRatingId());
            return stmt;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        final String sql = "UPDATE FILMS SET FILM_NAME = ?, FILM_DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, RATING_ID = ?"
            + " WHERE FILM_ID = ?";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getRatingId(),
                film.getId()
        );
        return film;
    }

    @Override
    public Optional<Film> filmById(Integer id) {
        final String sql = "SELECT * FROM FILMS" +
                " JOIN RATING ON FILMS.RATING_ID = RATING.RATING_ID" +
                //" JOIN FILM_GENRE ON FILMS.FILM_ID = FILM_GENRE.FILM_ID" +
                " WHERE FILMS.FILM_ID = ?";
        final List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> filmMapper(rs), id);
        return films.size() > 0 ? Optional.of(films.get(0)) : Optional.empty();
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
        filmOut.setRating(rating);
        return filmOut;
    }
}
