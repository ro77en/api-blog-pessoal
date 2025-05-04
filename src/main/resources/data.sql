INSERT INTO categories (id, title)
VALUES (1, 'General');

INSERT INTO categories (id, title)
VALUES (2, 'Specific');

ALTER TABLE categories ALTER COLUMN id RESTART WITH 3;
