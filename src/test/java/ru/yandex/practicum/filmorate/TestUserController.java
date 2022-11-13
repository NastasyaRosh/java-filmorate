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
    private User user;

    @BeforeEach
    public void beforeEach() {
        userController = new UserController();
        user = User.builder().email("mail@mail.com").login("login").name("name").birthday(LocalDate.of(1990, 1, 1)).build();
    }

    @Test
    public void notExist() {
        user.setId(1);

        assertThrows(FilmOrUserNotExist.class, () -> userController.updateUser(user), "Должно быть возвращено исключение.");
    }

    @Test
    public void rightValidation() {
        assertEquals(userController.addUser(user), user, "Добавлен не верный пользователь.");
    }

    @Test
    public void emptyName() {
        user.setName(null);
        User responseUser = userController.addUser(user);
        user.setName("login");
        user.setId(1);

        assertEquals(user, responseUser, "Имени не присвоен логин.");
    }
}
