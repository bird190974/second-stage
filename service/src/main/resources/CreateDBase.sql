-- CREATE DATABASE car_rental;

-- id идентификационный номер машины в службе проката
-- reg_sign государственный регистрационный знак автомобиля
-- model модель фирмы производителя
-- engine_category - дизель, бензин, гибрид, электромобиль
-- gearBox - автомат, робот, ручная
-- color - цвет
-- seats_quantity - вместимость (включая водителя)
-- image картинка авто на хостинге, облаке
CREATE TABLE car
(
    id SERIAL PRIMARY KEY,
    reg_sign VARCHAR(16)UNIQUE NOT NULL ,
    model VARCHAR(128) NOT NULL ,
    engine_category VARCHAR(32) NOT NULL ,
    gearbox VARCHAR(32) NOT NULL ,
    color VARCHAR(32),
    seats_quantity INT NOT NULL ,
    cost_per_day NUMERIC(8, 2) NOT NULL ,
    image VARCHAR(256)
);
-- DROP TABLE car;

-- -- пока таблица на размышление, если date_rent_possibility в прошлом относительно желаемого времени заказа, то
-- --     машина будет в категории доступных. Для недоступных, можно будет указать дату, с которой будет в доступе
-- -- возможно и ценник за день можно будет сделать рассчитываемым, cost_per_day - базовая цена, а дальше кэфами можно
-- -- будет повышать (хреновый клиент) или понижать (долгая аренда, плюшки и бонусы у клиента)
--
-- CREATE TABLE car_category
-- (
--     id INT REFERENCES car(id),
--     date_rent_possibility DATE ,
--     cost_per_day NUMERIC(8, 2)
-- );

-- DROP TABLE car_category;



CREATE TABLE users
(
    id SERIAL PRIMARY KEY ,
    first_name VARCHAR(32) NOT NULL ,
    last_name VARCHAR(32) NOT NULL ,
    email VARCHAR(128) UNIQUE NOT NULL ,
    password VARCHAR(32) NOT NULL ,
    role VARCHAR(16) NOT NULL
);
-- DROP TABLE users;

-- client_rating характеризует клиентскую историю, допустим по десятибальной шкале (1..10)
-- может служить основанием для отказа или для начисления бонусов
CREATE TABLE client
(
    id SERIAL PRIMARY KEY ,
    user_id INT REFERENCES users(id) ON DELETE CASCADE ,
    passport_no VARCHAR(128) UNIQUE ,
    driver_licence_no VARCHAR(128) UNIQUE ,
    driver_licence_expiration DATE NOT NULL ,
    credit_amount NUMERIC(8, 2) NOT NULL ,
    client_rating INT
);

-- DROP TABLE client;

CREATE TABLE orders
(
    id BIGSERIAL PRIMARY KEY ,
    user_id INT REFERENCES users(id),
    car_id INT REFERENCES car(id),
    begin_time TIMESTAMP NOT NULL ,
    end_time TIMESTAMP NOT NULL ,
    status VARCHAR(16) NOT NULL ,
    message VARCHAR(128)
);

-- DROP TABLE orders;



