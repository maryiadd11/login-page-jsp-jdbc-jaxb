DROP TABLE `Servlets_Part2`.`product`;
DROP TABLE `Servlets_Part2`.`user_account`;

CREATE TABLE `Servlets_Part2`.`user_account`
(
    `USER_NAME` varchar(30) NOT NULL,
    `GENDER`    varchar(1)  NOT NULL,
    `PASSWORD`  varchar(30) NOT NULL,
    PRIMARY KEY (`USER_NAME`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `Servlets_Part2`.`product`
(
    `CODE`  varchar(20)  NOT NULL,
    `NAME`  varchar(128) NOT NULL,
    `PRICE` float        NOT NULL,
    PRIMARY KEY (`CODE`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `Servlets_Part2`.`user_account`
(`USER_NAME`,
 `GENDER`,
 `PASSWORD`)
VALUES ('tom', 'M', 'tom001'),
       ('jerry', 'M', 'jerry001');

INSERT INTO `Servlets_Part2`.`product`
(`CODE`,
 `NAME`,
 `PRICE`)
VALUES ('P001', 'Java Core', 100),
       ('P002', 'C# Core', 90);