# java-filmorate

![ER-diagram](/Filmorate.png)

### Пояснение к схеме:
- жанры фильмов решено сделать как отдельную таблицу-справочник;
- лайки через "двойной ключ", то есть без id лайка;
- дружба пользователей друг с другом в одну строку, то есть при поиске будет использоваться запрос по обоим столбцам. 
- // Хорошая диаграмма, можно сделать так, чтобы в таблице friends ссылались напрямую в таблицу user, там типа круговая "один ко многим" будет, также вроде говорили про название столбцов лучше делать всегда такого вида "user_name" это к releaseDate, обрати внимание на отношение "один ко многим" это имеет значение, primary key должны быть в каждом столбце и лучше, чтобы у связанных таблиц совпадали названия столбцов, так проще будет ориентироваться
- // примеры запросов шикарные
- // код можно оформлять через - и 4 пробела

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

- SELECT u.login
- FROM users AS u
- LEFT JOIN friends AS f ON u.user_id = f.user_id_A
- LEFT JOIN f ON u.user_id = f.user_id_B
- WHERE ((f.user_id_A = 'ann') OR (f.user_id_B = 'ann'))
  - AND (u.verify = true);
