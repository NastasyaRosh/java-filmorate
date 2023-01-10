package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.FilmOrUserAlreadyExist;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userStorage;
    private final FriendDao friendDao;

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User addUser(User user) {
        setFullName(user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user) throws FilmOrUserNotExist {
        findUserById(user.getId());
        setFullName(user);
        return userStorage.updateUser(user);
    }

    public User findUserById(int id) throws FilmOrUserNotExist {
        return userStorage.findUserById(id).orElseThrow(() ->
                new FilmOrUserNotExist(String.format("Пользователь с ID %d не найден.", id)));
    }

    public User addFriend(int id, int friendId) {
        checkId(friendId);
        if (id == friendId) {
            throw new ValidationException("Пользователь пытается подружиться сам с собой.");
        }
        User user = findUserById(id);
        User friend = findUserById(friendId);
        if (findFriendsByUserId(id).contains(friend)) {
            throw new FilmOrUserAlreadyExist("Дружба уже зарегистрирована.");
        }
        friendDao.addFriend(id, friendId);
        return user;
    }

    public List<User> findFriendsByUserId(Integer userId) {
        return friendDao.findFriendsByUserId(userId);
    }

    public User deleteFriend(int id, int friendId) {
        checkId(friendId);
        User user = findUserById(id);
        User friend = findUserById(friendId);
        if (!findFriendsByUserId(id).contains(friend)) {
            throw new FilmOrUserNotExist("Такая дружба не была зарегистрирована.");
        }
        friendDao.deleteFriend(id, friendId);
        return user;
    }

    public List<User> findCommonFriends(int id, int otherId) {
        return friendDao.findCommonFriends(id, otherId);
    }

    private void setFullName(User user) {
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
