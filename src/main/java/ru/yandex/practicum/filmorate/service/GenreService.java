package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreDao genreDao;

    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

    public Genre findGenreById(Integer id) throws FilmOrUserNotExist {
        return genreDao.findGenreById(id).orElseThrow(() ->
                new FilmOrUserNotExist(String.format("Жанр с ID %d не найден.", id)));
    }
}
