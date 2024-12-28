-- address table
CREATE TABLE IF NOT EXISTS address(
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(50) NOT NULL,
    complement VARCHAR(50),
    city VARCHAR(100) NOT NULL,
    postal_code VARCHAR(10) NOT NULL,
    region VARCHAR(30),
    country_code VARCHAR(2) NOT NULL
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

-- LiveMessage table
CREATE TABLE IF NOT EXISTS live_message (
    id BIGSERIAL PRIMARY KEY,
    from_user_id BIGINT REFERENCES account(id) NOT NULL,
    to_user_id BIGINT REFERENCES account(id) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    content TEXT NOT NULL
);

-- Customer message table
CREATE TABLE IF NOT EXISTS customer_message (
    id BIGSERIAL PRIMARY KEY,
    from_user_id BIGINT REFERENCES account(id) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    content TEXT NOT NULL
);

-- Customer message reply table
CREATE TABLE IF NOT EXISTS customer_message_reply (
    id BIGSERIAL PRIMARY KEY,
    customer_message_id BIGINT REFERENCES customer_message(id) NOT NULL,
    from_user_id BIGINT REFERENCES account(id) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    content TEXT NOT NULL
);

-- Agency table
CREATE TABLE IF NOT EXISTS agency (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR (50) NOT NULL,
    address_id BIGINT REFERENCES address(id) NOT NULL
);

-- Agency opening days table
CREATE TABLE IF NOT EXISTS opening (
    agency_id BIGINT REFERENCES agency(id) NOT NULL,
    week_day INTEGER(1) NOT NULL,
    open_at TIME NOT NULL,
    close_at TIME NOT NULL,
    PRIMARY KEY(agency_id, week_day)
);

-- Vehicule table
CREATE TABLE IF NOT EXISTS vehicule (
    id BIGSERIAL PRIMARY KEY,
    registration_number VARCHAR(255) NOT NULL,
    category VARCHAR(1) NOT NULL,
    available BOOLEAN NOT NULL,
    agency_id BIGINT REFERENCES agency(id)
);
CREATE UNIQUE INDEX IF NOT EXISTS registration_number_index ON vehicule(registration_number);

-- Rental's reservation table
CREATE TABLE IF NOT EXISTS reservation (
    id BIGSERIAL PRIMARY KEY,
    creation_date TIMESTAMP,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    start_city VARCHAR(100),
    end_city VARCHAR(100),
    canceled BOOLEAN,
    account_id BIGINT REFERENCES reservation(id),
    payment_id BIGINT REFERENCES payment(id)
);

-- Payment table
CREATE TABLE IF NOT EXISTS payment (
    id BIGSERIAL PRIMARY KEY,
    invoice_id BIGINT REFERENCES invoice(id)
    price NUMERIC(9,2),
    currency VARCHAR(2) CHECK (currency IN ('$', '€', '£'))
);

-- Refund table
CREATE TABLE IF NOT EXISTS refund (
    id BIGSERIAL PRIMARY KEY,
    payment_id BIGINT REFERENCES payment(id),
    price NUMERIC(9,2),
    penality NUMERIC(3,2)
    currency VARCHAR(2) CHECK (currency IN ('$', '€', '£'))
);

-- Invoice table
CREATE TABLE IF NOT EXISTS invoice (
    num BIGSERIAL PRIMARY KEY,
    date TIMESTAMP,
    name VARCHAR(50),
    address VARCHAR(255),
    amount NUMERIC(9,2),
    currency VARCHAR(2) CHECK (currency IN ('$', '€', '£'))
);