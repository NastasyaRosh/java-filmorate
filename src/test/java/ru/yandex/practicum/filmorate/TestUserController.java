package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestUserController {
    private UserController userController;

    @BeforeEach
    public void beforeEach() {
        userController = new UserController();
    }

    @Test
    public void notExist() {
        User userWithFalseId = User.builder().id(1).email("mail@mail.com").login("login").name("name").birthday(LocalDate.of(1990, 1,1)).build();

        assertThrows(FilmOrUserNotExist.class, () -> userController.updateUser(userWithFalseId), "Должно быть возвращено исключение.");
    }

    @Test
    public void rightValidation() {
        User requestUser = User.builder().email("mail@mail.com").login("login").name("name").birthday(LocalDate.of(1990, 1,1)).build();
        User responseUser = userController.addUser(requestUser);

        assertEquals(responseUser, requestUser, "Добавлен не верный пользователь.");
    }

    @Test
    public void emptyName() {
        User requestUser = User.builder().email("mail@mail.com").login("login").name(null).birthday(LocalDate.of(1990, 1,1)).build();
        userController.addUser(requestUser);
        User responseUser = User.builder().id(1).email("mail@mail.com").login("login").name("login").birthday(LocalDate.of(1990, 1,1)).build();

        assertEquals(responseUser, requestUser, "Имени не присвоен логин.");
    }
}
