-- address table
CREATE TABLE IF NOT EXISTS address(
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    complement VARCHAR(255),
    city VARCHAR(255) NOT NULL,
    postal_code VARCHAR(10) NOT NULL,
    region VARCHAR(30),
    country_code VARCHAR(255) NOT NULL
);
CREATE INDEX IF NOT EXISTS city_index ON address(city);

-- User's account  table
CREATE TABLE IF NOT EXISTS account (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    title VARCHAR(4) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    role VARCHAR(25) NOT NULL,
    enabled BOOLEAN NOT NULL,
    address_id BIGINT REFERENCES address(id)
);
CREATE UNIQUE INDEX IF NOT EXISTS email_index ON account(email);


CREATE TABLE IF NOT EXISTS live_message (
    id BIGSERIAL PRIMARY KEY,
    from_user_id BIGINT REFERENCES account(id),
    to_user_id BIGINT REFERENCES account(id),
    created_at TIMESTAMP DEFAULT NOW(),
    content TEXT NOT NULL
);