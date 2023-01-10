package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAllGenres() {
        final String sql = "SELECT * FROM GENRE";
        return jdbcTemplate.query(sql, (rs, rowNum) -> genreMapper(rs));
    }

    @Override
    public Optional<Genre> findGenreById(Integer id) {
        String sql = "SELECT * FROM GENRE WHERE GENRE_ID = ?";
        final List<Genre> genres = jdbcTemplate.query(sql, (rs, rowNum) -> genreMapper(rs), id);
        return genres.size() > 0 ? Optional.of(genres.get(0)) : Optional.empty();
    }

    private Genre genreMapper(ResultSet resultSet) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("GENRE_ID"))
                .name(resultSet.getString("GENRE_NAME"))
                .build();
    }
}
