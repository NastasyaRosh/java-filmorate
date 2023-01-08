package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FriendDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FriendDaoImpl implements FriendDao {
    private final JdbcTemplate jdbcTemplate;

    public FriendDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        String sql = "INSERT INTO FRIENDS VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        String sql = "DELETE FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<User> friendsByUserId(Integer userId) {
        String sql = "SELECT * FROM USERS WHERE USERS.USER_ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?)";

        return jdbcTemplate.query(sql, (rs, rowNum) -> userMapper(rs), userId);
    }

    @Override
    public List<User> commonFriends(Integer userId, Integer friendId) {
        String sql = "SELECT * FROM USERS" +
                " WHERE USER_ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE FRIENDS.USER_ID = ?)" +
                " AND USER_ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE FRIENDS.USER_ID = ?)";
        return jdbcTemplate.query(sql, (rs, rowNum) -> userMapper(rs), userId, friendId);
    }

    private User userMapper(ResultSet resultSet) throws SQLException {
        User userOut = new User();
        userOut.setId(resultSet.getInt("USER_ID"));
        userOut.setEmail(resultSet.getString("EMAIL"));
        userOut.setLogin(resultSet.getString("LOGIN"));
        userOut.setName(resultSet.getString("USER_NAME"));
        userOut.setBirthday(resultSet.getDate("BIRTHDAY").toLocalDate());
        return userOut;
    }
}
