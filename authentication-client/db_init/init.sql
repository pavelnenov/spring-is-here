CREATE DATABASE IF NOT EXISTS authentication;

USE authentication;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
    );

-- insert into users (first_name, last_name, email, password) values ("Pavel", "Nenov", "pnnenov@gmail.com", "qwerty");
