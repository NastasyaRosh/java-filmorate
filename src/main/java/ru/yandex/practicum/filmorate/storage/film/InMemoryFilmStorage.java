package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    public static final LocalDate FIRST_FILM = LocalDate.of(1895, 12, 28);
    private Map<Integer, Film> films = new HashMap<>();

    private int id = 0;

    @Override
    public List<Film> allFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film addFilm(Film film) {
        validate(film);
        films.put(setId(film), film);
        return films.get(film.getId());
    }

    @Override
    public Film updateFilm(Film film) throws FilmOrUserNotExist {
        validate(film);
        checkExist(film.getId());
        films.replace(film.getId(), film);
        return films.get(film.getId());
    }

    @Override
    public Film filmById(int id) throws FilmOrUserNotExist {
        checkExist(id);
        return films.get(id);
    }

    private int setId(Film film) {
        film.setId(++this.id);
        return this.id;
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(FIRST_FILM)) {
            throw new ValidationException("Неверная дата создания фильма.");
        }
    }

    private void checkExist(int id) throws FilmOrUserNotExist {
        if (!(films.containsKey(id))) {
            throw new FilmOrUserNotExist("Фильма с таким ID не существует.");
        }
    }
}
