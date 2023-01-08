package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Service
public class GenreService {
    private final GenreDao genreDao;

    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public List<Genre> allGenres() {
        return genreDao.allGenres();
    }

    public Genre genreById(Integer id) throws FilmOrUserNotExist {
        Genre genreOut = genreDao.genreById(id).orElseThrow(() ->
                new FilmOrUserNotExist(String.format("Жанр с ID %d не найден.", id)));
        return genreOut;
    }
}
