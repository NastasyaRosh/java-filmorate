package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.FilmOrUserAlreadyExist;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class UserService {
    private final UserDao userStorage;
    private final FriendDao friendDao;

    @Autowired
    public UserService(UserDao userStorage, FriendDao friendDao) {
        this.userStorage = userStorage;
        this.friendDao = friendDao;
    }

    public List<User> allUsers() {
        return userStorage.allUsers();
    }

    public User addUser(User user) {
        forNullName(user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user) throws FilmOrUserNotExist {
        User userOut = userById(user.getId());
        forNullName(user);
        return userStorage.updateUser(user);
    }

    public User userById(int id) throws FilmOrUserNotExist {
        User userOut = userStorage.userById(id).orElseThrow(() ->
                new FilmOrUserNotExist(String.format("Пользователь с ID %d не найден.", id)));
        return userOut;
    }

    public User addFriend(int id, int friendId) {
        checkId(friendId);
        if (id == friendId) {
            throw new ValidationException("Пользователь пытается подружиться сам с собой.");
        }
        User user = userById(id);
        User friend = userById(friendId);
        if (allFriends(id).contains(friend)) {
            throw new FilmOrUserAlreadyExist("Дружба уже зарегистрирована.");
        }
        friendDao.addFriend(id, friendId);
        return user;
    }

    public List<User> allFriends(Integer userId) {
        return friendDao.friendsByUserId(userId);
    }

    public User deleteFriend(int id, int friendId) {
        checkId(friendId);
        User user = userById(id);
        User friend = userById(friendId);
        if (!allFriends(id).contains(friend)) {
            throw new FilmOrUserNotExist("Такая дружба не была зарегистрирована.");
        }
        friendDao.deleteFriend(id, friendId);
        return user;
    }

    public List<User> commonFriends(int id, int otherId) {
        return friendDao.commonFriends(id, otherId);
    }

    private void forNullName(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        } else if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    private void checkId(int id) {
        if (id <= 0) {
            throw new FilmOrUserNotExist("Передан неположительный ID.");
        }
    }

}
