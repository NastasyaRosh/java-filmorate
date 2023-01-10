package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.dao.impl.LikeDaoImpl;
import ru.yandex.practicum.filmorate.exception.FilmOrUserAlreadyExist;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private static final LocalDate EARLIEST_FIRST_DATE = LocalDate.of(1895, 12, 28);
    private final FilmDao filmStorage;
    private final FilmGenreDao filmGenreDao;

    private final LikeDaoImpl likeDao;
    private final UserService userService;

    public List<Film> getAllFilms() {
        List<Film> films = filmStorage.getAllFilms();
        for (Film film : films) {
            film.setGenres(filmGenreDao.findGenresByFilmId(film.getId()));
        }
        return films;
    }

    public Film addFilm(Film film) {
        validate(film);
        List<Genre> filmGenres = film.getGenres();
        Film filmOut = filmStorage.addFilm(film);
        if (filmGenres != null && !filmGenres.isEmpty()) {
            filmGenres = filmGenres.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
            for (Genre genre : filmGenres) {
                filmGenreDao.addFilmGenre(film.getId(), genre.getId());
            }
        }
        filmOut.setGenres(filmGenreDao.findGenresByFilmId(film.getId()));
        return filmOut;
    }

    public Film updateFilm(Film film) throws FilmOrUserNotExist {
        validate(film);
        Film filmOut = findFilmById(film.getId());
        List<Genre> filmGenres = film.getGenres();
        filmOut = filmStorage.updateFilm(film);
        filmGenreDao.deleteFilmGenre(film.getId());
        if (filmGenres != null && !filmGenres.isEmpty()) {
            filmGenres = filmGenres.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
            for (Genre genre : filmGenres) {
                filmGenreDao.addFilmGenre(film.getId(), genre.getId());
            }
        }
        filmOut.setGenres(filmGenreDao.findGenresByFilmId(film.getId()));
        return filmOut;
    }

    public Film findFilmById(int id) {
        Film filmOut = filmStorage.findFilmById(id).orElseThrow(() ->
                new FilmOrUserNotExist(String.format("Фильм с ID %d не найден.", id)));
        filmOut.setGenres(filmGenreDao.findGenresByFilmId(id));
        return filmOut;
    }

    public Film setLike(int filmId, int userId) {
        Film film = findFilmById(filmId);
        User user = userService.findUserById(userId);
        if (likeDao.findUsersByLikesFilm(userId).contains(user)) {
            throw new FilmOrUserAlreadyExist("Лайк уже есть.");
        }
        likeDao.addLike(filmId, userId);
        return film;
    }

    public Film deleteLike(int filmId, int userId) {
        Film film = findFilmById(filmId);
        User user = userService.findUserById(userId);
        if (!likeDao.findUsersByLikesFilm(filmId).contains(user)) {
            throw new FilmOrUserAlreadyExist("Нельзя удалить лайк, который еще не был поставлен.");
        }
        return film;
    }

    public List<Film> findPopularFilms(Integer count) {
        return likeDao.findAllPopular(count);
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(EARLIEST_FIRST_DATE)) {
            throw new ValidationException("Неверная дата создания фильма.");
        }
    }
}
