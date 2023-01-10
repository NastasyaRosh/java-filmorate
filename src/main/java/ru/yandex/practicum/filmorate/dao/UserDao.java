package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAllUsers();

    User addUser(User user);

    User updateUser(User user);

    Optional<User> findUserById(Integer id);
}
