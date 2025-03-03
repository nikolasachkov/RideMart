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

CREATE TABLE advertisement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    title VARCHAR(32) NOT NULL,
    description TEXT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE photo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    advertisement_id INT NOT NULL UNIQUE,
    photo_url VARCHAR(255),
    FOREIGN KEY (advertisement_id) REFERENCES advertisement(id)
);

CREATE TABLE motorbike_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    advertisement_id INT NOT NULL UNIQUE,
    price INT NOT NULL,
    make VARCHAR(32) NOT NULL,
    model VARCHAR(32) NOT NULL,
    year YEAR NOT NULL,
    mileage INT NOT NULL,
    engine_size INT NOT NULL,
    engine_type VARCHAR(16) NOT NULL,
    motorbike_type VARCHAR(16) NOT NULL,
    fuel_system_type VARCHAR(16) NOT NULL,
    FOREIGN KEY (advertisement_id) REFERENCES advertisement(id)
);
