-- CREATE DATABASE weather
-- WITH
-- OWNER = "postgres"
-- TEMPLATE = template0
-- ENCODING = 'UTF8'
-- LC_COLLATE = 'Russian_Russia.1251'
-- LC_CTYPE = 'Russian_Russia.1251'
-- TABLESPACE = pg_default
-- CONNECTION LIMIT = -1;
--
-- GRANT ALL ON DATABASE weather TO "postgres";

DROP TABLE requests_by_city, users;

CREATE TABLE users (
  id       SERIAL NOT NULL PRIMARY KEY,
  username TEXT   NOT NULL,
  password TEXT   NOT NULL,
  active   BOOLEAN   DEFAULT TRUE,
  created  TIMESTAMP DEFAULT NOW()
);

CREATE TABLE requests_by_city (
  id          SERIAL  NOT NULL PRIMARY KEY,
  user_id     INTEGER NOT NULL REFERENCES users (id),
  city        TEXT    NOT NULL,
  image_url   TEXT    NOT NULL,
  finished    BOOLEAN   DEFAULT FALSE,
  date        TIMESTAMP DEFAULT NOW(),
  temp        REAL,
  pressure    REAL,
  description TEXT,
  lon         REAL,
  lat         REAL
);

INSERT INTO users (username, password, active) VALUES
  ('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', TRUE),
  ('guest', '84983c60f7daadc1cb8698621f802c0d9f9a3c3c295c810748fb048115c186ec', TRUE);



