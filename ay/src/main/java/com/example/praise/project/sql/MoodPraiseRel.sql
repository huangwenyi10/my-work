-- auto Generated on 2017-09-16 23:18:44 
-- DROP TABLE IF EXISTS `mood_praise_rel`; 
CREATE TABLE `mood_praise_rel`(
    `id` VARCHAR (50) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'user_id',
    `mood_id` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'mood_id',
    `is_parise` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'is_parise',
    `praise_time` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT 'praise_time',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`mood_praise_rel`';
