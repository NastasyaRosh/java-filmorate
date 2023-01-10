package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAllUsers() {
        final String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> userMapper(rs));
    }

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY) VALUES(?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement prSt = con.prepareStatement(sql, new String[]{"USER_ID"});
            prSt.setString(1, user.getEmail());
            prSt.setString(2, user.getLogin());
            prSt.setString(3, user.getName());
            prSt.setDate(4, Date.valueOf(user.getBirthday()));
            return prSt;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE USERS SET EMAIL = ?, LOGIN = ?, USER_NAME = ?, BIRTHDAY = ? WHERE USER_ID = ?";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()),
                user.getId());
        return user;
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        final List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> userMapper(rs), id);
        return users.size() > 0 ? Optional.of(users.get(0)) : Optional.empty();
    }

    private User userMapper(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("USER_ID"))
                .email(resultSet.getString("EMAIL"))
                .login(resultSet.getString("LOGIN"))
                .name(resultSet.getString("USER_NAME"))
                .birthday(resultSet.getDate("BIRTHDAY").toLocalDate())
                .build();
    }
}
