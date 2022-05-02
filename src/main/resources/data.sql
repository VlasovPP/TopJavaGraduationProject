INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user@gmail.com', 'User_First', 'User_Last', '{noop}password'),
       ('admin@gmail.ru', 'Admin_First', 'Admin_Last', '{noop}admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (NAME, ADDRESS)
VALUES ('Сinema and germans', 'Lenin Street, 10, Krasnoyarsk 660001 Russia'),
       ('Pig and beads translation', 'Lenin Street, 11, Krasnoyarsk 660001 Russia'),
       ('Guests', 'Lenin Street, 17, Krasnoyarsk 660001 Russia'),
       ('Peppers', 'Lenin Street, 57А, Krasnoyarsk 660001 Russia'),
       ('Embers', 'Lenin Street, 18, Krasnoyarsk 660001 Russia');

INSERT INTO MENU (ADDED, RESTAURANT_ID)
VALUES (CURRENT_TIMESTAMP(), 1),
       (CURRENT_TIMESTAMP(), 2),
       ('2021-12-15', 3),
       (CURRENT_TIMESTAMP(), 4);

INSERT INTO DISH (DISH_NAME, DISH_PRICE, MENU_ID)
VALUES ('Beef steak', 400, 1),
       ('Marbled beef pie', 2000, 1),
       ('Fritatta with lobster and caviar', 1000, 1),
       ('Pizza "Louis"', 12000, 1),
       ('Fleurburger', 5000, 2),
       ('Dessert "Fisherman on stilts"', 14500, 2),
       ('Simply the most expensive sushi', 4500, 2),
       ('Diamond caviar', 34500, 2),
       ('Sunday Frozen Haute Chocolate', 25000, 2),
       ('Faberge chocolate pudding', 34500, 3),
       ('Hot Dog', 145, 3),
       ('Jamon Iberico de Beyota "Albarragena"', 180, 3),
       ('Buddha Jump Over the Wall Soup', 190, 3),
       ('Fish', 190, 4),
       ('Von Essen Platinum Club Sandwich', 197, 4);

INSERT INTO VOTE (VOTED, RESTAURANT_ID, USER_ID)
VALUES ('2021-12-15', 3, 1),
       ('2021-12-16', 3, 1),
       ('2021-12-17', 3, 2),
       ('2021-12-19', 3, 1),
       (CURRENT_TIMESTAMP(), 1, 2);