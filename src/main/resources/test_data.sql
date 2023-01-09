MERGE INTO RATING (RATING_ID, RATING_NAME)
    VALUES (1, 'G'),
           (2, 'PG'),
           (3, 'PG-13'),
           (4, 'R'),
           (5, 'NC-17');

MERGE INTO GENRE (GENRE_ID, GENRE_NAME)
    values (1, 'Комедия'),
           (2, 'Драма'),
           (3, 'Мультфильм'),
           (4, 'Триллер'),
           (5, 'Документальный'),
           (6, 'Боевик');

INSERT INTO USERS (LOGIN, EMAIL, USER_NAME, BIRTHDAY)
VALUES ('loginname', 'email@email.ru', 'username', PARSEDATETIME('01.01.1990', 'dd.MM.yyyy'));

INSERT INTO USERS (LOGIN, EMAIL, USER_NAME, BIRTHDAY)
VALUES ('userlogin', 'mail@mail.ru', 'username2', PARSEDATETIME('01.01.2000', 'dd.MM.yyyy'));

INSERT INTO FILMS (FILM_NAME, FILM_DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID)
VALUES ('oldFilm', 'Old Drama Comedy Film', PARSEDATETIME('01.01.1900', 'dd.MM.yyyy'), 160, 1);

INSERT INTO FILMS (FILM_NAME, FILM_DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID)
VALUES ('newFilm', 'It is new Action Film', PARSEDATETIME('01.01.2000', 'dd.MM.yyyy'), 120, 2);

INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID)
VALUES (1, 1);

INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID)
VALUES (1, 2);

INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID)
VALUES (2, 6);