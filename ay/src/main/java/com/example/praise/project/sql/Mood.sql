-- auto Generated on 2017-09-16 22:51:37 
-- DROP TABLE IF EXISTS `mood`; 
CREATE TABLE `mood`(
    `id` VARCHAR (50) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `content` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'content',
    `praise_num` INT (11) NOT NULL DEFAULT -1 COMMENT 'praise_num',
    `user_id` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'user_id',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`mood`';
