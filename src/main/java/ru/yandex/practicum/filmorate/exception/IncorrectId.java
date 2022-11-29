package ru.yandex.practicum.filmorate.exception;

public class IncorrectId extends RuntimeException{
    public IncorrectId(String message) {
        super(message);
    }
}
