package ru.yandex.practicum.filmorate.exception;

public class FilmOrUserAlreadyExist extends RuntimeException {
    public FilmOrUserAlreadyExist(String message) {
        super(message);
    }
}
