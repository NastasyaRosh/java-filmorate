package ru.yandex.practicum.filmorate.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.FilmOrUserAlreadyExist;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;

import javax.validation.ValidationException;

@RestControllerAdvice(assignableTypes = {FilmController.class, UserController.class})
public class ErrorHandler {
    @ExceptionHandler(FilmOrUserNotExist.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notExist(final RuntimeException e){ return new ErrorResponse(e.getMessage());}

    @ExceptionHandler({FilmOrUserAlreadyExist.class, ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse alreadyExist(final RuntimeException e){ return new ErrorResponse(e.getMessage());}

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse otherExceptions(final Throwable e) {return new ErrorResponse("Произошла непредвиденная ошибка.");}
}
