package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmOrUserNotExist;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        log.debug("Количество пользователей: " + userService.getAllUsers().size());
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) throws FilmOrUserNotExist {
        return userService.findUserById(id);
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
    public List<User> getAllFriends(@PathVariable int id) {
        log.debug(String.format("Количество друзей у пользователя с ID = %d равно %d.",
                id, userService.findFriendsByUserId(id).size()));
        return userService.findFriendsByUserId(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.debug(String.format("Количество общих друзей у пользователей с ID = %d и ID = %d равно %d.",
                id, otherId, userService.findCommonFriends(id, otherId).size()));
        return userService.findCommonFriends(id, otherId);
    }

}
