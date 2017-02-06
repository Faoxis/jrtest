CREATE TABLE IF NOT EXISTS task (
  id SERIAL NOT NULL,
  name VARCHAR(150) NOT NULL,
  done BOOLEAN NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO task(name, done) VALUES ('Вынести мусор', FALSE);
INSERT INTO task(name, done) VALUES ('Сходить за хлебом', TRUE);
INSERT INTO task(name, done) VALUES ('Победить дракона', FALSE);
INSERT INTO task(name, done) VALUES ('Спасти принцессу', TRUE);
INSERT INTO task(name, done) VALUES ('Сделать задание для JR', FALSE);
INSERT INTO task (name, done) VALUES ('Заполнить таблицу тестовыми данными', TRUE);


