-- 新增顶级菜单
-- set @parentId = 0;
-- set @systemCode = '';

-- 新增下级菜单
-- select id,system_code,menu_level from menu where id=@parentId;
set @parentId = 110;
set @systemCode = (select system_code from menu where id=@parentId);

-- 菜单SQL
INSERT INTO `menu` ( `system_code`, `title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id`, `name` )
    SELECT @systemCode,'路由规则配置', 'menu', '', '/route/routerule',
        'admin:route:routerule:index', 1, @parentId, 'routerule';

-- 按钮SQL(获取菜单id)
set @newParentId = @@identity;

INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'列表', 'button', '', '#',
     'admin:route:routerule:list', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'详情', 'button', '', '#',
     'admin:route:routerule:info', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'新增', 'button', '', '#',
     'admin:route:routerule:add', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'导入', 'button', '', '#',
     'admin:route:routerule:upload', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'更新', 'button', '', '#',
     'admin:route:routerule:edit', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'删除', 'button', '', '#',
     'admin:route:routerule:delete', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'下载', 'button', '', '#',
     'admin:route:routerule:download', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'审核', 'button', '', '#',
     'admin:route:routerule:auditing', 1,@newParentId;