SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


CREATE TABLE IF NOT EXISTS `nested_categories` (
  `name` varchar(50) NOT NULL,
  `id` int(11) NOT NULL,
  `lft` int(11) NOT NULL,
  `rgt` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

INSERT INTO `nested_categories` (`name`, `id`, `lft`, `rgt`) VALUES
('root', 1, 0, 1);

CREATE TABLE IF NOT EXISTS `orders` (
  `products` text NOT NULL,
  `price` int(11) NOT NULL,
  `userid` int(11) DEFAULT NULL,
  `date` varchar(50) NOT NULL,
  `id` int(11) NOT NULL,
  `usermail` varchar(300) NOT NULL,
  `checked` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `product` (
  `name` varchar(200) NOT NULL,
  `category` varchar(100) NOT NULL,
  `price` int(11) NOT NULL DEFAULT '0',
  `images` text NOT NULL,
  `info` text NOT NULL,
  `stock` int(11) NOT NULL DEFAULT '0',
  `id` int(11) NOT NULL,
  `rea` int(11) NOT NULL,
  `size` int(11) NOT NULL,
  `color` varchar(30) NOT NULL,
  `inOrder` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `user` (
  `name` varchar(50) NOT NULL,
  `mail` varchar(150) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `admin` tinyint(4) NOT NULL DEFAULT '0',
  `id` int(11) NOT NULL,
  `registerd` tinyint(4) NOT NULL DEFAULT '0',
  `adress` varchar(500) NOT NULL,
  `history` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

INSERT INTO `user` (`name`, `mail`, `pass`, `admin`, `id`, `registerd`, `adress`, `history`) VALUES
('Admin', 'kontakt.norr1945@gmail.com', 'MMDfQ#6#c#Y2ZRLMMcY2_vNMMMMMM.IGdVWwYRLMMllbca', 1, 1, 1, 'Kristina Nilssons Väg 9', '');


ALTER TABLE `nested_categories`
  ADD UNIQUE KEY `id` (`id`);

ALTER TABLE `orders`
  ADD UNIQUE KEY `id` (`id`);

ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `mail` (`mail`);


ALTER TABLE `nested_categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
