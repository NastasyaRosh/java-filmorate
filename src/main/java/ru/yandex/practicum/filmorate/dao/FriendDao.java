package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendDao {
    void addFriend(Integer userId, Integer friendId);

    void deleteFriend(Integer userId, Integer friendId);

    List<User> findFriendsByUserId(Integer userId);

    List<User> findCommonFriends(Integer userId, Integer friendId);
}
