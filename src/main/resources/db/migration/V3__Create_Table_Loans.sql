CREATE TABLE loan (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    loan_date DATE NOT NULL,
    return_date DATE NOT NULL,
    status VARCHAR(255) NOT NULL
);

CREATE SEQUENCE LOAN_SEQ
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

INSERT INTO loan (user_id, book_id, loan_date, return_date, status)
VALUES (1, 1, '2023-10-19', '2023-10-19', 'Pendente');

INSERT INTO loan (user_id, book_id, loan_date, return_date, status)
VALUES (1, 8, '2023-10-19', '2023-10-19', 'Pendente');

INSERT INTO loan (user_id, book_id, loan_date, return_date, status)
VALUES (2, 2, '2023-10-19', '2023-10-19', 'Pendente');

INSERT INTO loan (user_id, book_id, loan_date, return_date, status)
VALUES (3, 3, '2023-10-19', '2023-10-19', 'Pendente');