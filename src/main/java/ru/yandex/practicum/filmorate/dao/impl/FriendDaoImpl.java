package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FriendDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Repository
public class FriendDaoImpl implements FriendDao {
    private final JdbcTemplate jdbcTemplate;

    public FriendDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {

    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {

    }

    @Override
    public List<User> friendsByUserId(Integer userId) {
        return null;
    }

    @Override
    public List<User> commonFriends(Integer userId, Integer friendId) {
        return null;
    }
}
