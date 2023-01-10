package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmGenreDaoImpl implements FilmGenreDao {
    private final JdbcTemplate jdbcTemplate;

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
        return Genre.builder()
                .id(resultSet.getInt("GENRE_ID"))
                .name(resultSet.getString("GENRE_NAME"))
                .build();
    }
}
