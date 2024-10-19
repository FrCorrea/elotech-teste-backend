CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    register_date TIMESTAMP NOT NULL,
    phone_number VARCHAR(255) NOT NULL
);

CREATE SEQUENCE USERS_SEQ
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

INSERT INTO users (name, email, register_date, phone_number) VALUES ('John', 'john@gmail.com', '2023-10-19 10:18:21.000', '123456789');
INSERT INTO users (name, email, register_date, phone_number) VALUES ('Jane', 'jane@gmail.com', '2023-10-19 10:18:21.000', '987654321');
INSERT INTO users (name, email, register_date, phone_number) VALUES ('Bob', 'bob@gmail.com', '2023-10-19 10:18:21.000', '555555555');

