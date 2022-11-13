package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private HashMap<Integer, User> users = new HashMap<>();

    private int id = 0;

    private int setId(User user) {
        user.setId(++this.id);
        return this.id;
    }

    @GetMapping
    public Collection<User> allUsers(){
        log.debug("Количество пользователей: " + users.size());
        return users.values();
    }

    @PostMapping
        public User addUser(@Valid @RequestBody User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(setId(user), user);
        log.debug("Создан новый пользователь с ID: " + user.getId());
        return users.get(user.getId());
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws FilmOrUserNotExist {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (!(users.containsKey(user.getId()))) {
            log.warn("Введен неверный ID.");
            throw new FilmOrUserNotExist("Пользователя с таким ID не существует.");
        } else {
            users.replace(user.getId(), user);
            log.debug(String.format("Пользователь с ID = %d обновлен.", user.getId()));
        }
        return users.get(user.getId());
    }
}
