package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private Map<Integer, User> users = new HashMap<>();

    private int id = 0;

    @GetMapping
    public List<User> allUsers() {
        log.debug("Количество пользователей: " + users.size());
        return (List<User>) users.values();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        forNullName(user);
        users.put(setId(user), user);
        log.debug("Создан новый пользователь с ID: " + user.getId());
        return users.get(user.getId());
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws FilmOrUserNotExist {
        forNullName(user);
        checkExist(user);
        users.replace(user.getId(), user);
        log.debug(String.format("Пользователь с ID = %d обновлен.", user.getId()));
        return users.get(user.getId());
    }

    private int setId(User user) {
        user.setId(++this.id);
        return this.id;
    }

    private void forNullName(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }

    private void checkExist(User user) throws FilmOrUserNotExist {
        if (!(users.containsKey(user.getId()))) {
            log.warn("Введен неверный ID.");
            throw new FilmOrUserNotExist("Пользователя с таким ID не существует.");
        }
    }

}
