-- Address table
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

-- Live message table
CREATE TABLE IF NOT EXISTS live_message (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT NOW(),
    content TEXT NOT NULL,
    from_user_id BIGINT REFERENCES account(id) NOT NULL,
    to_user_id BIGINT REFERENCES account(id) NOT NULL
);

-- Customer message table
CREATE TABLE IF NOT EXISTS customer_message (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT NOW(),
    content TEXT NOT NULL,
    customer_id BIGINT REFERENCES account(id) NOT NULL
);

-- Customer message reply table
CREATE TABLE IF NOT EXISTS customer_message_reply (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT NOW(),
    content TEXT NOT NULL,
    customer_message_id BIGINT REFERENCES customer_message(id) NOT NULL,
    from_user_id BIGINT REFERENCES account(id) NOT NULL
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
    week_day INTEGER NOT NULL,
    open_at TIME NOT NULL,
    close_at TIME NOT NULL,
    PRIMARY KEY(agency_id, week_day)
);

-- Vehicule table
CREATE TABLE IF NOT EXISTS vehicule (
    id BIGSERIAL PRIMARY KEY,
    registration_number VARCHAR(10) NOT NULL,
    category VARCHAR(1) NOT NULL,
    available BOOLEAN NOT NULL,
    agency_id BIGINT REFERENCES agency(id)
);
CREATE UNIQUE INDEX IF NOT EXISTS registration_number_index ON vehicule(registration_number);

-- Payment table
CREATE TABLE IF NOT EXISTS payment (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP DEFAULT NOW(),
    price NUMERIC(9,2) NOT NULL,
    currency VARCHAR(2) CHECK (currency IN ('$', '€', '£')),
    account_id BIGINT REFERENCES account(id)
);

-- Rental's reservation table
CREATE TABLE IF NOT EXISTS reservation (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT NOW(),
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    start_city VARCHAR(100) NOT NULL,
    end_city VARCHAR(100) NOT NULL,
    canceled BOOLEAN DEFAULT false,
    account_id BIGINT REFERENCES account(id) NOT NULL,
    payment_id BIGINT REFERENCES payment(id) NOT NULL
);

-- Refund table
CREATE TABLE IF NOT EXISTS refund (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP DEFAULT NOW(),
    price NUMERIC(9,2),
    penalty NUMERIC(3,2) DEFAULT 0,
    currency VARCHAR(2) CHECK (currency IN ('$', '€', '£')),
    payment_id BIGINT REFERENCES payment(id) NOT NULL
);

-- Rental offer search view
DROP VIEW IF EXISTS rental_offer_vw;

CREATE VIEW rental_offer_vw AS
SELECT ag.id, ag.name,  ad.city, ve.category, op.week_day, op.close_at, op.open_at
FROM agency ag
LEFT JOIN address ad on ag.address_id=ad.id
LEFT JOIN opening op ON op.agency_id=ag.id
LEFT JOIN vehicule ve ON ve.agency_id=ag.id
WHERE ve.available
GROUP BY ag.id, ad.city, op.week_day, op.close_at, op.open_at, ve.category;

-- Usage
SELECT * from rental_offer_vw
WHERE city='PARIS'
AND week_day=1 -- Monday
AND ('09:30:00' BETWEEN  open_at AND  close_at)
AND category = 'V'