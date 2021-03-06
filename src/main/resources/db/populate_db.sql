DELETE FROM users;
DELETE FROM events;
DELETE FROM dates;
DELETE FROM tickets;
DELETE FROM user_discounts;
DELETE FROM total_discounts;
DELETE FROM event_info;

ALTER SEQUENCE user_seq RESTART WITH 100;
ALTER SEQUENCE event_seq RESTART WITH 100;
ALTER SEQUENCE date_seq RESTART WITH 100;
ALTER SEQUENCE ticket_seq RESTART WITH 100;

INSERT INTO users(first_name, last_name, email, password, birthday)
  VALUES ('Ivan', 'Kravchuk', 'kravchukivan@gmail.com', '$2a$04$BAuP/D2wxRuaCuCHVIjEk.Usua7TkbNc0RAFcrzNdbwlL1ls8r7C.', '1985-02-01 10:00'),
         ('Alek', 'Onopenko', 'onopemkoalek@gmail.com', '$2a$04$7Vz1Ets3LK50fTy0GZNE6.kHfG7q9SEje..ZNx13ojdsp6n44Jm..', '1993-04-07 10:00');

INSERT INTO user_roles (role, user_id) VALUES
        ('ROLE_USER', 100),
        ('ROLE_ADMIN', 100),
        ('ROLE_USER', 101);

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

INSERT INTO user_discounts(user_id, discount_type, amount)
  VALUES  (100, 'BIRTHDAY', 0),
          (100, 'TENTH_TICKET', 0),
          (101, 'BIRTHDAY', 0);

INSERT INTO total_discounts(discount_type, amount)
  VALUES  ('BIRTHDAY', 0),
          ('TENTH_TICKET', 0);

INSERT INTO event_info(event_id, count_by_name, count_price_queried, count_tickets_booked)
  VALUES (100, 0, 0, 0);