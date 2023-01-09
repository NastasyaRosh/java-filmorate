package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FilmGenreDaoImpl implements FilmGenreDao {
    private final JdbcTemplate jdbcTemplate;

    public FilmGenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFilmGenre(Integer idFilm, Integer idGenre) {
        String sql = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";
        jdbcTemplate.update(sql, idFilm, idGenre);
    }

    @Override
    public void deleteFilmGenre(Integer idFilm) {
        String sql = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, idFilm);
    }

    @Override
    public List<Genre> findGenresByFilmId(Integer id) {
        String sql = "SELECT GENRE.* FROM GENRE LEFT JOIN FILM_GENRE FG on GENRE.GENRE_ID = FG.GENRE_ID WHERE FILM_ID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> genreMapper(rs), id);
    }

    private Genre genreMapper(ResultSet resultSet) throws SQLException {
        Genre genreOut = new Genre();
        genreOut.setId(resultSet.getInt("GENRE_ID"));
        genreOut.setName(resultSet.getString("GENRE_NAME"));
        return genreOut;
    }
}
