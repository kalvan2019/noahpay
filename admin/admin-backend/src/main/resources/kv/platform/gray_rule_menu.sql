-- 新增顶级菜单
-- set @parentId = 0;
-- set @systemCode = '';

-- 新增下级菜单
-- select id,system_code,menu_level from menu where id=@parentId;
set @parentId = 1;
set @systemCode = (select system_code from menu where id=@parentId);

-- 菜单SQL
INSERT INTO `menu` ( `system_code`, `title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id`, `name` )
    SELECT @systemCode,'灰度规则配置', 'menu', '', '/gateway/grayrule',
        'admin:gateway:grayrule:index', 1, @parentId, 'grayrule';

-- 按钮SQL(获取菜单id)
set @newParentId = @@identity;

INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'列表', 'button', '', '#',
     'admin:gateway:grayrule:list', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'详情', 'button', '', '#',
     'admin:gateway:grayrule:info', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'新增', 'button', '', '#',
     'admin:gateway:grayrule:add', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'更新', 'button', '', '#',
     'admin:gateway:grayrule:edit', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'删除', 'button', '', '#',
     'admin:gateway:grayrule:delete', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'下载', 'button', '', '#',
     'admin:gateway:grayrule:download', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'审核', 'button', '', '#',
     'admin:gateway:grayrule:auditing', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'启用', 'button', '', '#',
     'admin:gateway:grayrule:enable', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'停用', 'button', '', '#',
     'admin:gateway:grayrule:disable', 1,@newParentId;