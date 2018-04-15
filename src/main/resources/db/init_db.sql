DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS dates CASCADE;
DROP TABLE IF EXISTS tickets CASCADE;
DROP SEQUENCE IF EXISTS user_seq CASCADE;
DROP SEQUENCE IF EXISTS event_seq CASCADE;
DROP SEQUENCE IF EXISTS date_seq CASCADE;
DROP SEQUENCE IF EXISTS ticket_seq CASCADE;

CREATE SEQUENCE user_seq START 100;
CREATE SEQUENCE event_seq START 100;
CREATE SEQUENCE date_seq START 100;
CREATE SEQUENCE ticket_seq START 100;

CREATE TABLE users(
  id            INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  first_name    VARCHAR NOT NULL,
  last_name     VARCHAR NOT NULL,
  email         VARCHAR UNIQUE,
  birthday      TIMESTAMP DEFAULT NULL
);

CREATE TABLE events(
  id            INTEGER PRIMARY KEY DEFAULT nextval('event_seq'),
  name          VARCHAR,
  base_price    DECIMAL,
  rating        VARCHAR NOT NULL
);

CREATE TABLE dates(
  id               INTEGER PRIMARY KEY DEFAULT nextval('date_seq'),
  event_date       TIMESTAMP,
  auditorium_name  VARCHAR NOT NULL,
  event_id         INTEGER
);

CREATE TABLE tickets(
  id                INTEGER PRIMARY KEY DEFAULT nextval('ticket_seq'),
  user_id           INTEGER,
  event_id          INTEGER,
  date_time         TIMESTAMP,
  seat              INTEGER,
  price             DECIMAL,
  discount          INTEGER,
  status            VARCHAR,
  booking_date_time TIMESTAMP DEFAULT now()
)
