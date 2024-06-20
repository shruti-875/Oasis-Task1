CREATE DATABASE reservation_system;

USE reservation_system;

CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50)
);

CREATE TABLE reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    train_number VARCHAR(20),
    train_name VARCHAR(100),
    class_type VARCHAR(20),
    date_of_journey DATE,
    from_place VARCHAR(100),
    to_place VARCHAR(100)
);

CREATE TABLE cancellations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reservation_id INT,
    pnr_number VARCHAR(20),
    FOREIGN KEY (reservation_id) REFERENCES reservations(id)
);

INSERT INTO users (username, password) VALUES ('user1', 'pass1');
