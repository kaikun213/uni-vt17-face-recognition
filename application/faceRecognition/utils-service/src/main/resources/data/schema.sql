DROP TABLE IF EXISTS `credentials`;
CREATE TABLE `credentials`
(
  `username` VARCHAR
(20) NOT NULL,
  `password` VARCHAR
(20) NOT NULL,
  PRIMARY KEY
(`username`))ENGINE
=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE
IF EXISTS `userentities`;
CREATE TABLE `userentities`
(
  `id` bigint NOT NULL AUTO_INCREMENT,
  `personalnumber` VARCHAR
(12) NOT NULL,
  `photolink` VARCHAR
(1024) NOT NULL,
  PRIMARY KEY
(`id`))ENGINE
=InnoDB DEFAULT CHARSET=utf8;