-- 新增顶级菜单
-- set @parentId = 0;
-- set @systemCode = '';

-- 新增下级菜单
-- select id,system_code,menu_level from menu where id=@parentId;
set @parentId = 109;
set @systemCode = (select system_code from menu where id=@parentId);

-- 菜单SQL
INSERT INTO `menu` ( `system_code`, `title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id`, `name` )
    SELECT @systemCode,'渠道行业类别转换对照表', 'menu', '', '/channel/channelmcc',
        'admin:channel:channelmcc:index', 1, @parentId, 'channelmcc';

-- 按钮SQL(获取菜单id)
set @newParentId = @@identity;

INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'列表', 'button', '', '#',
     'admin:channel:channelmcc:list', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'详情', 'button', '', '#',
     'admin:channel:channelmcc:info', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'新增', 'button', '', '#',
     'admin:channel:channelmcc:add', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'导入', 'button', '', '#',
     'admin:channel:channelmcc:upload', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'更新', 'button', '', '#',
     'admin:channel:channelmcc:edit', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'删除', 'button', '', '#',
     'admin:channel:channelmcc:delete', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'下载', 'button', '', '#',
     'admin:channel:channelmcc:download', 1,@newParentId;
INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'审核', 'button', '', '#',
     'admin:channel:channelmcc:auditing', 1,@newParentId;