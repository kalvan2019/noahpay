-- 新增顶级菜单
-- set @parentId = 0;
-- set @systemCode = '';

-- 新增下级菜单
-- select id,system_code,menu_level from menu where id=@parentId;
set @parentId = 114;
set @systemCode = (select system_code from menu where id=@parentId);

-- 菜单SQL
INSERT INTO `menu` ( `system_code`, `title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id`, `name` )
    SELECT @systemCode,'账户汇总日报表', 'menu', '', '/settle/accountreportday',
        'admin:settle:accountreportday:index', 1, @parentId, 'accountreportday';

-- 按钮SQL(获取菜单id)
set @newParentId = @@identity;

INSERT INTO `menu` ( `system_code`,`title`, `type`, `icon`, `path`, `code`, `priority`, `parent_id` )
    SELECT @systemCode,'列表', 'button', '', '#',
     'admin:settle:accountreportday:list', 1,@newParentId;
