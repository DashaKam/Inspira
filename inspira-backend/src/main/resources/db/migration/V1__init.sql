CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    nickname VARCHAR(30) UNIQUE NOT NULL,
    name VARCHAR(20) NOT NULL,
    password VARCHAR(256) NOT NULL
);

CREATE TABLE wish (
    id SERIAL PRIMARY KEY,
    sender_id INT NOT NULL REFERENCES users(id),
    receiver_id INT REFERENCES users(id),
    message VARCHAR(256) NOT NULL,
    anonymous BOOLEAN NOT NULL
);

CREATE TABLE settings (
    user_id INT PRIMARY KEY REFERENCES users(id),
    message_type VARCHAR(50) NOT NULL
)