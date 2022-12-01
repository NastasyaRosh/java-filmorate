package ru.yandex.practicum.filmorate.exception;

public class FilmOrUserNotExist extends RuntimeException {
    public FilmOrUserNotExist(String message) {
        super(message);
    }
}
