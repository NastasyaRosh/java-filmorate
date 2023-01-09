DROP TABLE if exists USERS, FILMS, FILM_GENRE, RATING, GENRE, LIKES, FRIENDS;

create table IF NOT EXISTS GENRE
(
    GENRE_ID   INTEGER auto_increment,
    GENRE_NAME CHARACTER VARYING(200) not null,
    constraint GENRE_PK
        primary key (GENRE_ID)
);

create table IF NOT EXISTS RATING
(
    RATING_ID          INTEGER auto_increment,
    RATING_NAME        CHARACTER VARYING(50) not null,
    RATING_DESCRIPTION CHARACTER VARYING(200),
    constraint RATING_PK
        primary key (RATING_ID)
);

create table IF NOT EXISTS FILMS
(
    FILM_ID          INTEGER auto_increment
        primary key,
    FILM_NAME        CHARACTER VARYING(200) not null,
    FILM_DESCRIPTION CHARACTER VARYING(1000),
    RELEASE_DATE     DATE,
    DURATION         INTEGER,
    RATING_ID        INTEGER,
    constraint FILMS_RATING_RATING_ID_FK
        foreign key (RATING_ID) references RATING
            on delete set null
);

create table IF NOT EXISTS FILM_GENRE
(
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint FILM_GENRE_PK
        primary key (FILM_ID, GENRE_ID),
    constraint FILM_GENRE_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS
            on delete cascade,
    constraint FILM_GENRE_GENRE_GENRE_ID_FK
        foreign key (GENRE_ID) references GENRE
            on delete cascade
);

create table IF NOT EXISTS USERS
(
    USER_ID   INTEGER auto_increment
        primary key,
    EMAIL     CHARACTER VARYING(50) not null,
    LOGIN     CHARACTER VARYING(50) not null,
    USER_NAME CHARACTER VARYING(50),
    BIRTHDAY  DATE
);

create table IF NOT EXISTS FRIENDS
(
    USER_ID   INTEGER not null,
    FRIEND_ID INTEGER not null,
    constraint FRIENDS_PK
        primary key (USER_ID, FRIEND_ID),
    constraint FRIENDS_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS
            on delete cascade,
    constraint FRIENDS_USERS_USER_ID_FK_2
        foreign key (FRIEND_ID) references USERS
);

create table IF NOT EXISTS LIKES
(
    USER_ID INTEGER not null,
    FILM_ID INTEGER not null,
    constraint LIKES_PK
        primary key (USER_ID, FILM_ID),
    constraint LIKES_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS
            on delete cascade,
    constraint LIKES_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS
            on delete cascade
);

