DELETE FROM users;
DELETE FROM events;
DELETE FROM dates;
DELETE FROM tickets;

ALTER SEQUENCE user_seq RESTART WITH 100;
ALTER SEQUENCE event_seq RESTART WITH 100;
ALTER SEQUENCE date_seq RESTART WITH 100;
ALTER SEQUENCE ticket_seq RESTART WITH 100;

INSERT INTO users(first_name, last_name, email, birthday)
  VALUES ('Ivan', 'Kravchuk', 'kravchukivan@gmail.com', '1985-05-01 10:00'),
         ('Alek', 'Onopenko', 'onopemkoalek@gmail.com', '1993-04-07 10:00');

INSERT INTO events(name, base_price, rating)
  VALUES ('The Lord Of The Rings', 65.00, 'HIGH'),
         ('Scary movie', 60.00, 'MID');

INSERT INTO dates(event_date, auditorium_name, event_id)
  VALUES ('2018-05-05 20:45', 'green', 100),
         ('2018-05-06 18:30', 'red', 100),
         ('2018-04-25 18:30', 'red', 101);

INSERT INTO tickets(user_id, event_id, date_time, seat, price, discount)
  VALUES (100, 100, '2018-05-05 20:45', 10, 80.00, 0),
         (101, 101, '2018-04-25 18:30', 42, 70.00, 0);