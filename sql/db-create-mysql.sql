DROP DATABASE IF EXISTS shop;
CREATE DATABASE shop;
USE shop;

CREATE TABLE roles
(
  id   INT         NOT NULL UNIQUE PRIMARY KEY,
  name VARCHAR(30) NOT NULL
);

INSERT INTO roles (id, name) VALUES
  (0, 'admin'),
  (1, 'client');

/******************************
Create users table
*******************************/
CREATE TABLE users
(
  id         INT         NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  login      VARCHAR(50) NOT NULL UNIQUE,
  password   VARCHAR(50) NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name  VARCHAR(50) NOT NULL,
  email      VARCHAR(50) NOT NULL UNIQUE,
  is_blocked BOOLEAN     NOT NULL        DEFAULT FALSE,
  role_id    INT         NOT NULL,
  CONSTRAINT roles_role_id_fk
  FOREIGN KEY (role_id)
  REFERENCES roles (id)
    ON DELETE CASCADE
);

INSERT INTO users (id, login, password, first_name, last_name, email, role_id) VALUES
  (1, 'admin', 'admin', 'olga', 'erokhina', 'email1@email.com', 0),
  (2, 'user1', 'pass', 'vasya', 'petrov', 'email2@email.com', 1),
  (3, 'user2', 'pass', 'kkk', 'aaaa', 'email3@email.com', 1);

/******************************
Create categories table
*******************************/
CREATE TABLE categories
(
  id            INT         NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  category_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO categories (id, category_name) VALUES
  (1, 'Computers'),
  (2, 'Phones'),
  (3, 'TVAndCameras'),
  (4, 'HouseAppliances');

/******************************
Create groups table
*******************************/
CREATE TABLE groups
(
  id          INT         NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  group_name  VARCHAR(30) NOT NULL,
  category_id INT         NOT NULL,
  CONSTRAINT categories_category_id_fk
  FOREIGN KEY (category_id)
  REFERENCES categories (id)
    ON DELETE CASCADE
);

INSERT INTO groups (id, group_name, category_id) VALUES
  (1, 'Laptops', 1),
  (2, 'Tablets', 1),
  (3, 'Office equipment', 1),
  (4, 'Computer Accessories', 1),
  (5, 'Smartphones', 2),
  (6, 'Mobile phones', 2),
  (7, 'Office phones', 2),
  (8, 'Phone Accessories', 2),
  (9, 'TV', 3),
  (10, 'Cameras', 3),
  (11, 'Videocameras', 3),
  (12, 'Audio', 3),
  (13, 'Fridges', 4),
  (14, 'Washing machines', 4),
  (15, 'Conditioners', 4),
  (16, 'Vacuum cleaners', 4);

/******************************
Create manufacturers table
*******************************/
CREATE TABLE manufacturers
(
  id                INT         NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  manufacturer_name VARCHAR(30) NOT NULL
);

INSERT INTO manufacturers (id, manufacturer_name) VALUES
  (1, 'Asus'),
  (2, 'Acer'),
  (3, 'HP'),
  (4, 'Apple'),
  (5, 'Samsung'),
  (6, 'Canon'),
  (7, 'Epson'),
  (8, 'Panasonic'),
  (9, 'Intel'),
  (10, 'AMD'),
  (11, 'Kingston'),
  (12, 'Sony'),
  (13, 'Philips'),
  (14, 'Sven'),
  (15, 'Bosh'),
  (16, 'LG'),
  (17, 'Toshiba'),
  (18, 'Dell'),
  (19, 'Microsoft'),
  (20, 'Nokia');

/******************************
Create colors table
*******************************/
CREATE TABLE colours
(
  id          INT         NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  colour_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO colours (id, colour_name) VALUES
  (1, 'Black'),
  (2, 'White'),
  (3, 'Grey'),
  (4, 'Red'),
  (5, 'Blue');

/******************************
Create goods table
*******************************/
CREATE TABLE goods
(
  id              INT         NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  name            VARCHAR(50) NOT NULL UNIQUE,
  price           INT         NOT NULL,
  release_date    DATE        NOT NULL,
  colour_id       INT         NOT NULL,
  group_id        INT         NOT NULL,
  manufacturer_id INT         NOT NULL,

  CONSTRAINT colours_colour_id_fk
  FOREIGN KEY (colour_id)
  REFERENCES colours (id)
    ON DELETE CASCADE,

  CONSTRAINT groups_group_id_fk
  FOREIGN KEY (group_id)
  REFERENCES groups (id)
    ON DELETE CASCADE,

  CONSTRAINT manufacturers_manufacturer_id_fk
  FOREIGN KEY (manufacturer_id)
  REFERENCES manufacturers (id)
    ON DELETE CASCADE
);

INSERT INTO goods (id, name, price, release_date, colour_id, group_id, manufacturer_id) VALUES
  (1, 'Asus VivoBook', 7449, '2016-04-01', 2, 1, 1),
  (2, 'Apple iPhone 7', 22999, '2016-11-04', 1, 5, 4),
  (3, 'HP 250 G5', 6999, '2015-05-29', 1, 1, 3),
  (4, 'Dell Inspiron 5558', 11599, '2015-01-24', 1, 1, 18),
  (5, 'Apple iPad Air 2 32Gb', 12500, '2015-11-13', 2, 2, 4),
  (6, 'Microsoft Surface', 4100, '2014-12-07', 1, 2, 19),
  (7, 'Flash Memory Kingston DataTraveler 16Gb', 179, '2013-08-27', 1, 4, 11),
  (8, 'Web Camera Samsung Gear 360', 9100, '2015-10-10', 2, 4, 5),
  (9, 'Printer Epson L850', 10600, '2015-07-17', 1, 3, 7),
  (10, 'SSD Kingston 120GB SATA3', 1540, '2014-03-15', 3, 4, 11),
  (11, 'Samsundg Galaxy S7 Edge 32Gb', 23000, '2016-02-14', 2, 5, 5),
  (12, 'Nokia 222 Dual Sim', 1100, '2016-04-01', 1, 6, 20),
  (13, 'TV Philips 32PFS', 10699, '2016-05-21', 3, 9, 13),
  (14, 'TV Sony KDL32', 7300, '2015-09-03', 1, 9, 12),
  (15, 'Camera Canon EOS 700D', 17000, '2014-11-17', 1, 10, 6),
  (16, 'Fridge Samsung RB29', 13199, '2015-12-14', 3, 13, 5),
  (17, 'Vacuum cleaner BOSCH BSN17', 2100, '2014-05-13', 4, 16, 15),
  (18, 'Vacuum cleaner BOSCH BGL 45Z', 8239, '2016-04-10', 4, 16, 15),
  (19, 'Conditioner LG CA 12 RWK ', 31599, '2016-07-01', 1, 15, 16),
  (20, 'Conditioner TOSHIBA RAS-07SK', 9300, '2015-05-21', 2, 15, 17),
  (21, 'Washing machine LG FH296WDS', 13699, '2015-04-17', 2, 14, 16),
  (22, 'Audio system Sony GTK-XB7', 9400, '2015-06-06', 5, 12, 12),
  (23, 'Video camera Panasonic HC-V7', 11499, '2016-02-13', 1, 11, 8),
  (24, 'Office phone Panasonic KX-TG25', 849, '2013-12-26', 1, 7, 8),
  (25, 'Samsung B350E Duos', 999, '2014-07-03', 5, 6, 5),
  (26, 'Samsung Galaxy A5', 9100, '2016-08-16', 2, 5, 5),
  (27, 'Monitor 27" LG 27MP', 6699, '2015-04-20', 1, 4, 16),
  (28, 'Apple A1706 MacBook Pro', 64699, '2016-09-12', 3, 1, 4);

/******************************
Create order table
*******************************/
CREATE TABLE orders
(
  id      INT         NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  date    DATE        NOT NULL,
  status  VARCHAR(30) NOT NULL,
  price   INT         NOT NULL,
  user_id INT         NOT NULL,

  CONSTRAINT users_user_id_fk
  FOREIGN KEY (user_id)
  REFERENCES users (id)
    ON DELETE CASCADE
);

/******************************
Create order_items table
*******************************/
CREATE TABLE order_items
(
  id       INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  amount   INT NOT NULL,
  order_id INT NOT NULL,
  goods_id INT NOT NULL,

  CONSTRAINT goods_goods_id_fk
  FOREIGN KEY (goods_id)
  REFERENCES goods (id)
    ON DELETE CASCADE,

  CONSTRAINT orders_order_id_fk
  FOREIGN KEY (order_id)
  REFERENCES orders (id)
    ON DELETE CASCADE
);