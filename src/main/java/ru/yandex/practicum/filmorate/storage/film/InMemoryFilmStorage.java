package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Integer, Film> films = new HashMap<>();

    private int id = 0;

    @Override
    public List<Film> allFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film addFilm(Film film) {
        films.put(setId(film), film);
        return films.get(film.getId());
    }

    @Override
    public Film updateFilm(Film film) throws FilmOrUserNotExist {
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

    private void checkExist(int id) throws FilmOrUserNotExist {
        if (!(films.containsKey(id))) {
            throw new FilmOrUserNotExist("Фильма с таким ID не существует.");
        }
    }
}
