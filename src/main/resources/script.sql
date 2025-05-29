DROP DATABASE IF EXISTS ridemart;
CREATE DATABASE ridemart;
USE ridemart;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(32) NOT NULL UNIQUE,
    email VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME
);

CREATE TABLE make (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE model (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    make_id INT NOT NULL,
    FOREIGN KEY (make_id) REFERENCES make(id) ON DELETE CASCADE
);

CREATE TABLE motorbike_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    price INT NOT NULL,
    model_id INT NOT NULL,
    year YEAR NOT NULL,
    mileage INT NOT NULL,
    engine_size INT NOT NULL,
    engine_type VARCHAR(16) NOT NULL,
    motorbike_type VARCHAR(16) NOT NULL,
    fuel_system_type VARCHAR(16) NOT NULL,
    FOREIGN KEY (model_id) REFERENCES model(id) ON DELETE CASCADE
);

CREATE TABLE advertisement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    motorbike_details_id INT NOT NULL UNIQUE,
    title VARCHAR(64) NOT NULL,
    description TEXT,
    city VARCHAR(32) NOT NULL,
    street VARCHAR(32) NOT NULL,
    street_number VARCHAR(8) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (motorbike_details_id) REFERENCES motorbike_details(id) ON DELETE CASCADE
);

CREATE TABLE photo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    advertisement_id INT NOT NULL,
    photo_url VARCHAR(255),
    FOREIGN KEY (advertisement_id) REFERENCES advertisement(id) ON DELETE CASCADE
);

CREATE TABLE saved_advertisement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    advertisement_id INT NOT NULL,
    UNIQUE(user_id, advertisement_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (advertisement_id) REFERENCES advertisement(id) ON DELETE CASCADE
);

ALTER TABLE model ADD CONSTRAINT unique_model_make UNIQUE (name, make_id);
