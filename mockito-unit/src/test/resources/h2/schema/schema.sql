CREATE TABLE `city`
(
    `id`       bigint                            NOT NULL AUTO_INCREMENT,
    `name`     varchar(64)                                 NOT NULL COMMENT '名称',
    `ctime`    datetime                                NOT NULL DEFAULT current_time,
    `utime`    datetime                                NOT NULL DEFAULT current_time on update current_time,
    `operator` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='城市表';
CREATE TABLE `sku`
(
    `id`       bigint                              NOT NULL AUTO_INCREMENT,
    `name`      varchar(64)                              NOT NULL COMMENT '名称',
    `pic`      varchar(256)                            not null comment 'sku照片',
    `ctime`    datetime                                NOT NULL DEFAULT current_time,
    `utime`    datetime                                NOT NULL DEFAULT current_time on update current_time,
    `operator` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='SKU表'