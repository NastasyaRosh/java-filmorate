package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    List<User> allUsers();

    User addUser(User user);

    User updateUser(User user) throws FilmOrUserNotExist;

    User userById(int id) throws FilmOrUserNotExist;
}
