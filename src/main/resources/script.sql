DROP DATABASE IF EXISTS ridemart;
CREATE DATABASE ridemart;
USE ridemart;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(32) NOT NULL UNIQUE,
    email VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(32) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME
);
