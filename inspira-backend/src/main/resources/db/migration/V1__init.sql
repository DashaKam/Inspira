CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    nickname VARCHAR(30) UNIQUE NOT NULL,
    name VARCHAR(20) NOT NULL,
    password VARCHAR(256) NOT NULL,
    message_type VARCHAR(50) NOT NULL
);

CREATE TABLE wish (
    id SERIAL PRIMARY KEY,
    sender_id INT,
    receiver_id INT,
    message VARCHAR(256) NOT NULL
)