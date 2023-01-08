package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private static final LocalDate FIRST_FILM = LocalDate.of(1895, 12, 28);
    private static final int DEFAULT_COUNT = 10;
    private final FilmDao filmStorage;

    @Autowired
    public FilmService(FilmDao filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> allFilms() {
        return filmStorage.allFilms();
    }

    public Film addFilm(Film film) {
        validate(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) throws FilmOrUserNotExist {
        validate(film);
        return filmStorage.updateFilm(film);
    }

    public Film filmById(int id) {
        Film filmOut = filmStorage.filmById(id).orElseThrow(() ->
                new FilmOrUserNotExist(String.format("Фильм с ID %d не найден.", id)));
        return filmOut;
    }

/*    public Film setLike(int id, int userId) {
        filmStorage.filmById(id).setLike(userId);
        return filmStorage.filmById(id);
    }

    public Film deleteLike(int id, int userId) {
        checkId(userId);
        Film film = filmStorage.filmById(id);
        if (film.getLikes().contains(userId)) {
            film.deleteLike(userId);
        }
        return film;
    }*/

    public List<Film> popularFilms(Integer count) {
        if (count == null) {
            count = DEFAULT_COUNT;
        }
        return filmStorage.allFilms().stream().
                sorted((f1, f2) -> (f2.getLikes().size() - f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    private void checkId(int id) {
        if (id <= 0) {
            throw new FilmOrUserNotExist("Передан неположительный ID.");
        }
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(FIRST_FILM)) {
            throw new ValidationException("Неверная дата создания фильма.");
        }
    }
}
