-- 2013/10/11
ALTER TABLE  `interface_info` DROP  `city_id`;

CREATE TABLE IF NOT EXISTS `service_city_interface` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_id` int(11) NOT NULL,
  `interface_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;