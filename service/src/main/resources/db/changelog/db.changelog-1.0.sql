--liquibase formatted sql

--changeset bird:1
CREATE TABLE IF NOT EXISTS car
(
    id              SERIAL PRIMARY KEY,
    reg_sign        VARCHAR(16) UNIQUE NOT NULL,
    model           VARCHAR(128)       NOT NULL,
    engine_category VARCHAR(32)        NOT NULL,
    gearbox         VARCHAR(32)        NOT NULL,
    color           VARCHAR(32),
    seats_quantity  INT                NOT NULL,
    cost_per_day    NUMERIC(8, 2)      NOT NULL,
    image           VARCHAR(256)
);
--rollback DROP TABLE car;

--changeset bird:2
CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(32)         NOT NULL,
    last_name  VARCHAR(32)         NOT NULL,
    email      VARCHAR(128) UNIQUE NOT NULL,
    password   VARCHAR(32)         NOT NULL,
    role       VARCHAR(16)         NOT NULL
);
--rollback DROP TABLE users;

--changeset bird:3
CREATE TABLE IF NOT EXISTS client
(
    id                        SERIAL PRIMARY KEY,
    user_id                   INT REFERENCES users (id) ON DELETE CASCADE,
    passport_no               VARCHAR(128) UNIQUE,
    driver_licence_no         VARCHAR(128) UNIQUE,
    driver_licence_expiration DATE          NOT NULL,
    credit_amount             NUMERIC(8, 2) NOT NULL,
    client_rating             INT
);
--rollback DROP TABLE client;

--changeset bird:4
CREATE TABLE IF NOT EXISTS orders
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    INT REFERENCES users (id) ON DELETE CASCADE ,
    car_id     INT REFERENCES car (id) ON DELETE CASCADE ,
    begin_time TIMESTAMP   NOT NULL,
    end_time   TIMESTAMP   NOT NULL,
    status     VARCHAR(16) NOT NULL,
    message    VARCHAR(128)
);
--rollback DROP TABLE orders;


