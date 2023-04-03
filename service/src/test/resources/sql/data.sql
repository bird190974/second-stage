INSERT INTO car (id, reg_sign, model, engine_category, gearbox, color, seats_quantity, cost_per_day, image)
VALUES (1, '1122 IX-2', 'Audi Q3', 'GAS', 'AUTOMATIC', 'Yellow', 5, 10.44, 'NoImage'),
       (2, '1123 IX-2', 'Tesla S3', 'ELECTRIC', 'AUTOMATIC', 'Black', 5, 13.44, 'NoImage'),
       (3, '1124 IX-2', 'Skoda Kodiaq', 'DIESEL', 'ROBOT', 'Green', 7, 8.44, 'NoImage');
SELECT setval('car_id_seq', (SELECT MAX(id) FROM car));

INSERT INTO users (id, first_name, last_name, email, password, role)
VALUES (1, 'Ivan', 'Ivanov', 'ivan@gmail.com', '1111', 'USER'),
       (2, 'Petr', 'Petrov', 'petr@gmail.com', '2222', 'USER'),
       (3, 'Sveta', 'Svetikova', 'sveta@gmail.com', '3333', 'USER');
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO client (id, user_id, passport_no, driver_licence_no, driver_licence_expiration, credit_amount,
                    client_rating)
VALUES (1, 1, '123I456', 'I123456', '2023-12-31', 120.45, 5),
       (2, 2, '123P456', 'P123456', '2023-6-30', 255.55, 5),
       (3, 3, '123S456', 'S123456', '2023-1-10', 111.11, 1);
SELECT setval('client_id_seq', (SELECT MAX(id) FROM client));

INSERT INTO orders (id, user_id, car_id, begin_time, end_time, status, message)
VALUES (1, 1, 3, '2023-03-27 10:00:00.000000', '2023-04-07 10:00:00.000000', 'ACCEPTED', 'Nice to see you'),
       (2, 2, 2, '2023-03-27 10:00:00.000000', '2023-04-02 10:00:00.000000', 'IN_PROGRESS', 'Please wait'),
       (3, 3, 3, '2023-03-27 10:00:00.000000', '2023-04-17 10:00:00.000000', 'DENIED', 'Sorry');
SELECT setval('orders_id_seq', (SELECT MAX(id) FROM orders));
