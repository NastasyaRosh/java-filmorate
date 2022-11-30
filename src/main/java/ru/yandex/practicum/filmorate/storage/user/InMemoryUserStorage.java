package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmOrUserAlreadyExist;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Map<Integer, User> users = new HashMap<>();

    private int id = 0;

    @Override
    public List<User> allUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User addUser(User user) {
        if (checkEmail(user.getEmail()).isPresent()) {
            throw new FilmOrUserAlreadyExist(String.format("Пользователь с почтой %s уже существует.", user.getEmail()));
        }
        users.put(setId(user), user);
        return users.get(user.getId());
    }

    @Override
    public User updateUser(User user) throws FilmOrUserNotExist {
        checkExist(user.getId());
        users.replace(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public User userById(int id) throws FilmOrUserNotExist {
        checkExist(id);
        return users.get(id);
    }

    private int setId(User user) {
        user.setId(++this.id);
        return this.id;
    }

    private void checkExist(int id) throws FilmOrUserNotExist {
        if (!(users.containsKey(id))) {
            throw new FilmOrUserNotExist(String.format("Пользователя с id = %d не существует.", id));
        }
    }

    private Optional<User> checkEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }
}
