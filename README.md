# java-filmorate

![ER-diagramm](/java-filmorate/Filmorate.png)

### Пояснение к схеме:
- жанры фильмов решено сделать как отдельную таблицу-справочник;
- лайки через "Двойной ключ", то есть без id лайка;
- дружба пользователей друг с другом в одну строку, то есть при поиске будет использоваться запрос по обоим столбцам.

### Примеры запросов:
**1. Поиск фильмов по названию жанра**
- SELECT f.name
- FROM films AS f
- LEFT JOIN film_genre AS fg ON f.film_id = fg.film_id
- LEFT JOIN genre AS g ON g.genre_id = fg.genre_id
- WHERE g.name = 'Комедия';

**2. Поиск количества лайков у 10 самых популярных фильмов**
- SELECT f.name,
  - COUNT(l.film_id) AS count_likes
- FROM films AS f
- LEFT JOIN likes AS l ON f.film_id = l.film_id
- GROUP BY f.name
- ORDER BY count_likes DESC
- LIMIT 10;

**3. Поиск всех фильмов, которые понравились пользователю по его логину**
- SELECT f.name
- FROM films AS f
- LEFT JOIN likes AS l ON f.film_id = l.film_id
- LEFT JOIN users AS u ON l.user_id = u.user_id
- WHERE u.login = 'ann';

**4. Поиск всех друзей пользователя по его логину**

> Есть сомнения на счет корректности этого запроса.

- SELECT u.login
- FROM users AS u
- LEFT JOIN friends AS f ON u.user_id = f.user_id_A
- LEFT JOIN f ON u.user_id = f.user_id_B
- WHERE ((f.user_id_A = 'ann') OR (f.user_id_B = 'ann'))
  - AND (u.verify = true);