CREATE TABLE User(
id INT,
name VARCHAR(100) NOT NULL,
username VARCHAR(50) NOT NULL,
password VARCHAR(255) NOT NULL
);
CREATE INDEX idx_user_id ON User (id);