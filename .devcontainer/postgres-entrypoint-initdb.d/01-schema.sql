-- user table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    birthdate DATE NOT NULL,
    roles VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE UNIQUE INDEX IF NOT EXISTS email_index ON users(email);

-- address table
CREATE TABLE IF NOT EXISTS address(
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(1000) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255),
    zipcode VARCHAR(255) NOT NULL,
    country VARCHAR(255),
    timezone VARCHAR(255)
);

CREATE INDEX IF NOT EXISTS city_index ON address(city);
CREATE INDEX IF NOT EXISTS zipcode_index ON address(zipcode);

-- users/address junction table
CREATE TABLE IF NOT EXISTS users_address(
    user_id BIGSERIAL REFERENCES users(id) NOT NULL,
    address_id BIGSERIAL REFERENCES address(id) NOT NULL,
    PRIMARY KEY(user_id, address_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS user_address_index ON users_address(user_id, address_id);