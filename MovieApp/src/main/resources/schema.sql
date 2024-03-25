CREATE SEQUENCE customer1 AS INTEGER START WITH 1;
CREATE SEQUENCE genre1 AS INTEGER START WITH 1;
CREATE SEQUENCE session1 AS INTEGER START WITH 1;
CREATE SEQUENCE language1 AS INTEGER START WITH 1;
CREATE SEQUENCE movie1 AS INTEGER START WITH 1;
CREATE SEQUENCE movieGenre1 AS INTEGER START WITH 1;
CREATE SEQUENCE customerSession1 AS INTEGER START WITH 1;



CREATE TABLE customers (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('customer1'),
    firstName varchar(100)  NOT NULL,
    lastName varchar(100)  NOT NULL,
    birthDate date  NOT NULL,
    email varchar(50)  NOT NULL,
);

CREATE TABLE genres (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('genre1'),
    text varchar(100)  NOT NULL,
);


CREATE TABLE "languages" (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('language1'),
    text varchar(60)  NOT NULL,

);


CREATE TABLE movies (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('movie1'),
    title varchar(100)  NOT NULL,
    ageRestrictions varchar(10)  NOT NULL,
    duration time  NOT NULL,
    releaseDate date  NOT NULL,

);

CREATE TABLE movieGenres (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('movieGenre1'),
    genre_id BIGINT  NOT NULL,
    movie_id BIGINT  NOT NULL,
    FOREIGN KEY (genre_id)
        REFERENCES genres ON DELETE CASCADE,
    FOREIGN KEY (movie_id)
        REFERENCES movies ON DELETE CASCADE
);


CREATE TABLE sessions (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('session1'),
    movie_id BIGINT  NOT NULL,
    language_id BIGINT  NOT NULL,
    hallNr varchar(20)  NOT NULL,
    startAt timestamp  NOT NULL,
    price decimal(2,2)  NOT NULL,
    FOREIGN KEY (language_id)
        REFERENCES "languages" ON DELETE CASCADE,
    FOREIGN KEY (movie_id)
        REFERENCES movies ON DELETE CASCADE

);


CREATE TABLE customerSessions (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('customerSession1'),
    session_id BIGINT  NOT NULL,
    customer_id BIGINT  NOT NULL,
    rowNr int  NOT NULL,
    seatNr int  NOT NULL,
    FOREIGN KEY (session_id)
        REFERENCES sessions ON DELETE CASCADE,
    FOREIGN KEY (customer_id)
        REFERENCES customers ON DELETE CASCADE
);


