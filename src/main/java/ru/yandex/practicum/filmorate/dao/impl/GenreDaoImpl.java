package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> allGenres() {
        final String sql = "SELECT * FROM GENRE";
        return jdbcTemplate.query(sql, (rs, rowNum) ->genreMapper(rs));
    }

    @Override
    public Optional<Genre> genreById(Integer id) {
        String sql = "SELECT * FROM GENRE WHERE GENRE_ID = ?";
        final List<Genre> genres = jdbcTemplate.query(sql, (rs, rowNum) -> genreMapper(rs), id);
        return genres.size() > 0 ? Optional.of(genres.get(0)) : Optional.empty();
    }

    private Genre genreMapper(ResultSet resultSet) throws SQLException {
        Genre genreOut = new Genre();
        genreOut.setId(resultSet.getInt("GENRE_ID"));
        genreOut.setName(resultSet.getString("GENRE_NAME"));
        return genreOut;
    }
}
