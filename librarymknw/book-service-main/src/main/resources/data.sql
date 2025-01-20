DROP TABLE IF EXISTS books_authors;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS editor;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS reservation;

CREATE TABLE IF NOT EXISTS editor  (
	editor_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR (255) NOT NULL,
    PRIMARY KEY (editor_id)
);

CREATE TABLE IF NOT EXISTS author (
	author_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR (255) NOT NULL,
    surname VARCHAR (255) NOT NULL,
    PRIMARY KEY (author_id)
);

create table book (
	book_id BIGINT NOT NULL AUTO_INCREMENT,
	title VARCHAR (255) NOT NULL,
	isbn VARCHAR (255) NOT NULL,
	editor BIGINT NOT NULL,
	PRIMARY KEY (book_id),
	FOREIGN KEY (editor) REFERENCES editor(editor_id)
);

CREATE TABLE IF NOT EXISTS books_authors (
    book BIGINT,
    author BIGINT,
    FOREIGN KEY (book) REFERENCES book(book_id),
    FOREIGN KEY (author) REFERENCES author(author_id)
);

CREATE TABLE user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE reservation (
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    reservation_date DATE NOT NULL,
    due_date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (book_id) REFERENCES book(book_id)
);

INSERT INTO editor (name) VALUES ('John Smith');
INSERT INTO editor (name) VALUES ('Emily Johnson');
INSERT INTO editor (name) VALUES ('Michael Brown');
INSERT INTO editor (name) VALUES ('Sophia Lee');
INSERT INTO editor (name) VALUES ('David Wilson');

INSERT INTO book (title, ISBN, editor) VALUES ('The Great Gatsby', '9780743273565', 1);
INSERT INTO book (title, ISBN, editor) VALUES ('To Kill a Mockingbird', '9780061120084', 2);
INSERT INTO book (title, ISBN, editor) VALUES ('1984', '9780451524935', 3);
INSERT INTO book (title, ISBN, editor) VALUES ('Pride and Prejudice', '9780141439518', 4);
INSERT INTO book (title, ISBN, editor) VALUES ('Harry Potter and the Sorcerer''s Stone', '9780590353427', 5);

INSERT INTO author (name, surname) VALUES ('Jane', 'Austen');
INSERT INTO author (name, surname) VALUES ('George', 'Orwell');
INSERT INTO author (name, surname) VALUES ('F. Scott', 'Fitzgerald');
INSERT INTO author (name, surname) VALUES ('Harper', 'Lee');
INSERT INTO author (name, surname) VALUES ('J.K.', 'Rowling');

INSERT INTO books_authors (book, author) VALUES (1, 1);  -- Jane Austen per "Pride and Prejudice"
INSERT INTO books_authors (book, author) VALUES (2, 4);  -- Harper Lee per "To Kill a Mockingbird"
INSERT INTO books_authors (book, author) VALUES (3, 2);  -- George Orwell per "1984"
INSERT INTO books_authors (book, author) VALUES (4, 1);  -- Jane Austen per "Emma"
INSERT INTO books_authors (book, author) VALUES (5, 5);  -- J.K. Rowling per "Harry Potter and the Sorcerer's Stone"


insert into user (name, email)
values
('Piero Piermenti', 'pieropiermenti@gmail.com'),
('Silvia Losinvia', 'losinginvia@pmail.com'),
('Croc Odillo', 'temagno@dmail.com')
;

INSERT INTO reservation (user_id, book_id, reservation_date, due_date)
VALUES
(1, 1, '2024-12-01', '2024-12-15'),
(2, 2, '2024-12-05', '2024-12-20');