-- auto Generated on 2017-09-16 22:07:22 
-- DROP TABLE IF EXISTS `user`; 
CREATE TABLE `user`(
    `id` VARCHAR (50) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'name',
    `account` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'account',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`user`';