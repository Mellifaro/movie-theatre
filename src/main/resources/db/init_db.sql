DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS dates CASCADE;
DROP TABLE IF EXISTS tickets CASCADE;
DROP TABLE IF EXISTS user_discounts CASCADE;
DROP TABLE IF EXISTS total_discounts CASCADE;
DROP SEQUENCE IF EXISTS user_seq CASCADE;
DROP SEQUENCE IF EXISTS event_seq CASCADE;
DROP SEQUENCE IF EXISTS date_seq CASCADE;
DROP SEQUENCE IF EXISTS ticket_seq CASCADE;
DROP SEQUENCE IF EXISTS discount_seq CASCADE;

CREATE SEQUENCE user_seq START 100;
CREATE SEQUENCE event_seq START 100;
CREATE SEQUENCE date_seq START 100;
CREATE SEQUENCE ticket_seq START 100;
CREATE SEQUENCE discount_seq START 100;

CREATE TABLE users(
  id            INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  first_name    VARCHAR NOT NULL,
  last_name     VARCHAR NOT NULL,
  email         VARCHAR UNIQUE,
  birthday      TIMESTAMP DEFAULT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE events(
  id            INTEGER PRIMARY KEY DEFAULT nextval('event_seq'),
  name          VARCHAR NOT NULL,
  base_price    DECIMAL NOT NULL,
  rating        VARCHAR NOT NULL
);

CREATE TABLE dates(
  id               INTEGER PRIMARY KEY DEFAULT nextval('date_seq'),
  event_date       TIMESTAMP NOT NULL,
  auditorium_name  VARCHAR NOT NULL,
  event_id         INTEGER NOT NULL,
  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);

CREATE TABLE tickets(
  id                INTEGER PRIMARY KEY DEFAULT nextval('ticket_seq'),
  user_id           INTEGER,
  event_id          INTEGER NOT NULL,
  date_time         TIMESTAMP NOT NULL,
  seat              INTEGER NOT NULL,
  price             DECIMAL NOT NULL,
  discount          INTEGER DEFAULT 0,
  discount_type     VARCHAR DEFAULT NULL,
  booking_date_time TIMESTAMP DEFAULT now(),
  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);

CREATE TABLE user_discounts(
  id                INTEGER PRIMARY KEY DEFAULT nextval('discount_seq'),
  user_id           INTEGER NOT NULL,
  discount_type     VARCHAR NOT NULL,
  amount            INTEGER DEFAULT NULL
);
CREATE UNIQUE INDEX user_discount_idx ON user_discounts (user_id, discount_type);

CREATE TABLE total_discounts(
  discount_type     VARCHAR PRIMARY KEY,
  amount            INTEGER DEFAULT NULL
);
