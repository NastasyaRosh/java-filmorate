package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Film> getAllFilms() {
        final String sql = "SELECT * FROM FILMS" +
                " JOIN RATING ON FILMS.RATING_ID = RATING.RATING_ID" +
                " GROUP BY FILMS.FILM_ID";
        return jdbcTemplate.query(sql, (rs, rowNum) -> filmMapper(rs));
    }

    @Override
    public Film addFilm(Film film) {
        String sql = "INSERT INTO FILMS (FILM_NAME, FILM_DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
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
                film.getMpa().getId(),
                film.getId()
        );
        return film;
    }

    @Override
    public Optional<Film> findFilmById(Integer id) {
        final String sql = "SELECT * FROM FILMS" +
                " JOIN RATING ON FILMS.RATING_ID = RATING.RATING_ID" +
                " WHERE FILMS.FILM_ID = ?";
        final List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> filmMapper(rs), id);
        return films.size() > 0 ? Optional.of(films.get(0)) : Optional.empty();
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
