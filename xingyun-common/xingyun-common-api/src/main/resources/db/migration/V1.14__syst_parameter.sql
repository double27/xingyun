DROP TABLE IF EXISTS `sys_parameter`;
CREATE TABLE `sys_parameter`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pm_key`      varchar(100) NOT NULL COMMENT '键',
    `pm_value`    varchar(200)          DEFAULT NULL COMMENT '值',
    `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `create_by`   varchar(32)  NOT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(32)  NOT NULL COMMENT '修改人',
    `update_time` datetime     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pm_key` (`pm_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统参数';

INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_time`,
                             `update_by`, `update_time`)
VALUES (1, 'tx-map.key', 'OLJBZ-ZFJK6-QWUSK-MB7XT-6UTN2-AWBSY', '腾讯地图Key', '1', '2022-05-22 04:18:59', '1', '2022-05-22 04:18:59');
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_time`,
                             `update_by`, `update_time`)
VALUES (2, 'tx-map.secret', 'secret', '腾讯地图Secret', '1', '2022-05-22 04:18:59', '1',
        '2022-05-22 04:18:59');

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                        `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                        `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('1000007', '1000007', 'SysParameter', '系统参数', '/system/parameter/index', '1000',
        '/parameter', 0, 1, 0, 'system:parameter:query', 1, 1, '', '1', '2021-05-08 18:37:01', '1',
        '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                        `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                        `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('1000007001', '1000007001', '', '新增系统参数', '', '1000007', '', 0, 2, 0,
        'system:parameter:add', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                        `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                        `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('1000007002', '1000007002', '', '修改系统参数', '', '1000007', '', 0, 2, 0,
        'system:parameter:modify', 1, 1, '', '1', '2021-05-12 23:23:33', '1',
        '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                        `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                        `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('1000007003', '1000007003', '', '删除系统参数', '', '1000007', '', 0, 2, 0,
        'system:parameter:delete', 1, 1, '', '1', '2021-05-12 23:24:36', '1',
        '2021-07-04 00:34:23');