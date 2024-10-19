CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL,
    publish_date DATE NOT NULL,
    category VARCHAR(255) NOT NULL
);

CREATE SEQUENCE BOOK_SEQ
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

INSERT INTO book (title, author, isbn, publish_date, category)
VALUES ('1984', 'George Orwell', '978-0-452-28423-4', '2023-08-12', 'Dystopian');

INSERT INTO book (title, author, isbn, publish_date, category)
VALUES ('To Kill a Mockingbird', 'Harper Lee', '978-0-06-112008-4', '2023-05-10', 'Classic');

INSERT INTO book (title, author, isbn, publish_date, category)
VALUES ('The Catcher in the Rye', 'J.D. Salinger', '978-0-316-76948-0', '2023-06-15', 'Classic');

INSERT INTO book (title, author, isbn, publish_date, category)
VALUES ('Brave New World', 'Aldous Huxley', '978-0-06-085052-4', '2023-07-21', 'Science Fiction');

INSERT INTO book (title, author, isbn, publish_date, category)
VALUES ('Moby Dick', 'Herman Melville', '978-0-14-243724-7', '2023-09-05', 'Adventure');

INSERT INTO book (title, author, isbn, publish_date, category)
VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', '978-0-7432-7356-5', '2023-04-01', 'Classic');

INSERT INTO book (title, author, isbn, publish_date, category)
VALUES ('Fahrenheit 451', 'Ray Bradbury', '978-1-4516-7331-9', '2023-03-10', 'Dystopian');

INSERT INTO book (title, author, isbn, publish_date, category)
VALUES ('The Chronicles of Narnia', 'C.S. Lewis', '978-0-06-447119-0', '2023-11-01', 'Fantasy');

INSERT INTO book (title, author, isbn, publish_date, category)
VALUES ('The Lord of the Rings', 'J.R.R. Tolkien', '978-0-618-00222-8', '2023-10-05', 'Fantasy');

INSERT INTO book (title, author, isbn, publish_date, category)
VALUES ('The Alchemist', 'Paulo Coelho', '978-0-06-231500-7', '2023-12-01', 'Philosophy');


