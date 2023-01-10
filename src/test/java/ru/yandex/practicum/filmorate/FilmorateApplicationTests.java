package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.dao.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql({"/schema.sql", "/test_data.sql"})
class FilmorateApplicationTests {
    private final UserDao userDao;
    private final FriendDao friendDao;

    private final FilmDao filmDao;
    private final FilmGenreDao filmGenreDao;
    private final RatingDao ratingDao;

    @Test
    public void testFindUserById() {

        Optional<User> userOptional = userDao.findUserById(1);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = userDao.getAllUsers();

        assertEquals(2, users.size(), "Не все пользователи были возвращены из БД.");
    }

    @Test
    public void testAddFriends() {
        friendDao.addFriend(1, 2);
        Optional<User> friend = friendDao.findFriendsByUserId(1).stream().findFirst();
        assertThat(friend)
                .isPresent()
                .hasValueSatisfying(user -> assertThat(user).hasFieldOrPropertyWithValue("id", 2));
        assertEquals(userDao.findUserById(2), friend, "Друг не был возвращен из БД.");
    }

    @Test
    public void testFindFilmById() {

        Optional<Film> filmOptional = filmDao.findFilmById(1);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void testFindGenresByFilmId() {
        List<Genre> genres = filmGenreDao.findGenresByFilmId(1);

        assertEquals(2, genres.size(), "Не возвращены жанры фильма ID=1");
        assertEquals("Комедия", genres.get(0).getName(), "Возвращен некорректный жанр");
    }

    @Test
    void testFindRatingByFilm() {
        Optional<Rating> ratingOptional = ratingDao.findRatingById(2);

        assertThat(ratingOptional)
                .isPresent()
                .hasValueSatisfying(rating ->
                        assertThat(rating).hasFieldOrPropertyWithValue("id", 2)
                );
        assertThat(ratingOptional)
                .isPresent()
                .hasValueSatisfying(rating ->
                        assertThat(rating).hasFieldOrPropertyWithValue("name", "PG")
                );
    }
}
