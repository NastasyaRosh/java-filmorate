package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> allFilms() {
        return filmStorage.allFilms();
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) throws FilmOrUserNotExist {
        return filmStorage.updateFilm(film);
    }

    public Film filmById(int id) {
        return filmStorage.filmById(id);
    }

    public Film setLike(int id, int userId) {
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
    }

    public List<Film> popularFilms(Integer count) {
        if (count == null) {
            count = 10;
        }
        return filmStorage.allFilms().stream().sorted((f1, f2) -> (f2.getLikes().size() - f1.getLikes().size())).limit(count).collect(Collectors.toList());
    }

    private void checkId(int id) {
        if (id <= 0) {
            throw new FilmOrUserNotExist("Передан неположительный ID.");
        }
    }
}
