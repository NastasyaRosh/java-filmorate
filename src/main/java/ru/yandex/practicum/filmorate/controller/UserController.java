package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> allUsers() {
        log.debug("Количество пользователей: " + userService.allUsers().size());
        return userService.allUsers();
    }

    @GetMapping("/{id}")
    public User userById(@PathVariable int id) throws FilmOrUserNotExist {
        return userService.userById(id);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.debug("Создание нового пользователя: " + user.getEmail());
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws FilmOrUserNotExist {
        log.debug(String.format("Обновление пользователя с ID = %d.", user.getId()));
        return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.debug(String.format("Добавление в друзья пользователю с ID = %d пользователя с ID = %d.", id, friendId));
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.debug(String.format("Удаление из друзей пользователя с ID = %d пользователя с ID = %d.", id, friendId));
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> allFriends(@PathVariable int id) {
        log.debug(String.format("Количество друзей у пользователя с ID = %d равно %d.", id, userService.allFriends(id).size()));
        return userService.allFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.debug(String.format("Количество общих друзей у пользователей с ID = %d и ID = %d равно %d.", id, otherId, userService.commonFriends(id, otherId).size()));
        return userService.commonFriends(id, otherId);
    }

}
