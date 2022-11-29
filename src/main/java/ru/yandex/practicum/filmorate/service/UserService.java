package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> allUsers() {
        return userStorage.allUsers();
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) throws FilmOrUserNotExist {
        return userStorage.updateUser(user);
    }

    public User userById(int id) throws FilmOrUserNotExist {
        return userStorage.userById(id);
    }

    public User addFriend(int id, int friendId) {
        checkId(friendId);
        if (id == friendId) {
            throw new ValidationException("Пользователь пытается подружиться сам с собой.");
        }
        userStorage.userById(id).addFriend(friendId);
        userStorage.userById(friendId).addFriend(id);
        return userStorage.userById(id);
    }

    public User deleteFriend(int id, int friendId) {
        checkId(friendId);
        User user = userStorage.userById(id);
        User friend = userStorage.userById(friendId);
        if (user.getFriends().contains(friendId) && friend.getFriends().contains(id)) {
            user.deleteFriend(friendId);
            friend.deleteFriend(id);
        }
        return user;
    }

    public List<User> allFriends(int id) {
        return userById(id).getFriends().stream().map(this::userById).collect(Collectors.toList());
    }

    public List<User> commonFriends(int id, int otherId) {
        return userById(id).getFriends().stream().filter(userById(otherId).getFriends()::contains).map(this::userById).collect(Collectors.toList());
    }

    private void checkId(int id) {
        if (id <= 0) {
            throw new FilmOrUserNotExist("Передан неположительный ID.");
        }
    }

}
