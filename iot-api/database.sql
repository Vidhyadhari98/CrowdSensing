CREATE TABLE train
(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE station
(
    name VARCHAR(256) NOT NULL PRIMARY KEY,
    length DOUBLE NOT NULL
);

CREATE TABLE coach
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    position INT UNSIGNED NOT NULL,
    train_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (train_id) REFERENCES train (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO train ()
VALUES (),
       (),
       (),
       ();

INSERT INTO coach (train_id, position)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (3, 1),
       (3, 2),
       (4, 1),
       (4, 2);

INSERT INTO station (name, length)
VALUES ('Kista', 6),
       ('T-Central', 6),
       ('Gullmarsplan', 6),
       ('Solna', 6);