package com.noahpay.pay.platform.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.system.SystemUtil;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.PermissionType;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.AdminMapper;
import com.noahpay.pay.commons.db.platform.mapper.MenuMapper;
import com.noahpay.pay.commons.db.platform.model.Admin;
import com.noahpay.pay.commons.db.platform.model.Menu;
import com.noahpay.pay.commons.util.TokenUtils;
import com.noahpay.pay.commons.vo.system.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Service
public class AuthService {
    @Resource
    protected AdminMapper adminMapper;
    @Resource
    protected MenuMapper menuMapper;

    /**
     * 登录
     */
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public UserVO login(String userCode, String password) {
        Admin admin = adminMapper.selectByUserCode(userCode);
        if (admin == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS.formatMessage("用户"));
        }
        if (admin.getState() != SwitchEnum.OPEN.code) {
            throw new BizException(AdminReturnCode.DATA_STATE_ERROR.formatMessage("用户状态未生效"));
        }
        //最小化更新token
        Admin updateDb = new Admin();
        updateDb.setId(admin.getId());
        String tempPassword = DigestUtil.md5Hex(password.toLowerCase() + admin.getSalt());
        if (!admin.getPassword().equals(tempPassword)) {
            throw new BizException(AdminReturnCode.PARAM_VALIDATE_ERROR.formatMessage("用户密码输入有误"));
        }
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        updateDb.setLastLoginIp(ServletUtil.getClientIP(request));
        updateDb.setLoginCount(admin.getLoginCount() + 1);
        updateDb.setToken(TokenUtils.createToken(userCode));
        updateDb.setTokenExpire(TokenUtils.createTokenExpire());
        updateDb.setLastLoginTime(DateUtil.date());
        int count = adminMapper.updateByUkSelective(updateDb);
        if (count < 1) {
            throw new BizException(AdminReturnCode.ERROR);
        }
        return convertUserVO(admin, updateDb.getToken(), updateDb.getTokenExpire());
    }

    /**
     * 检查token
     */
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public UserVO checkToken(String token) {
        Admin query = new Admin();
        query.setToken(token);
        Admin admin = adminMapper.selectOne(query);
        if (admin == null) {
            return null;
        }
        if (admin.getTokenExpire() == null
                || admin.getTokenExpire().getTime() < System.currentTimeMillis()) {
            return null;
        }
        //最小化更新token失效时间
        Admin updateDb = new Admin();
        updateDb.setId(admin.getId());
        updateDb.setTokenExpire(TokenUtils.createTokenExpire());
        int count = adminMapper.updateByUkSelective(updateDb);
        if (count < 1) {
            throw new BizException(AdminReturnCode.ERROR);
        }
        return convertUserVO(admin, admin.getToken(), updateDb.getTokenExpire());
    }

    private static UserVO convertUserVO(Admin admin, String token, Date tokenExpire) {
        UserVO userVO = new UserVO();
        userVO.setId(admin.getId());
        userVO.setUsername(admin.getUserCode());
        userVO.setNickName(admin.getUserName());
        userVO.setToken(token);
        userVO.setTokenExpire(tokenExpire);
        userVO.setLastLoginTime(admin.getLastLoginTime());
        return userVO;
    }

    /**
     * 检查权限
     */
    public boolean checkPermission(String token, List<String> needPermission) {
        Admin query = new Admin();
        query.setToken(token);
        Admin admin = adminMapper.selectOne(query);
        if (admin == null) {
            return false;
        }
        if (admin.getTokenExpire() == null || admin.getTokenExpire().getTime() < System.currentTimeMillis()) {
            return false;
        }
        //获取用户的权限
        Set<String> codes = menuMapper.selectAllCodeByAdminId(null, admin.getId());
        long result = needPermission.stream().filter(codes::contains).count();
        return needPermission.size() == result;
    }

    /**
     * 获取用户的权限
     */
    public Set<String> selectCodeByUserId(String systemCode, Long id) {
        return menuMapper.selectAllCodeByAdminId(systemCode, id);
    }

    /**
     * 获取用户的权限树
     */
    public List<Menu> selectMenuByUserId(String systemCode, Long id) {
        List<Menu> permissionList = menuMapper.selectAllMenuByAdminId(systemCode, id);
        String language = com.kalvan.client.context.RequestContextHolder.getContext().getLanguage();
        if (StringUtils.isNotBlank(language)
                && SystemUtil.constants != null
                && SystemUtil.constants.getI18nConfig() != null
                && SystemUtil.constants.getI18nConfig().getMenu() != null
                && SystemUtil.constants.getI18nConfig().getMenu().get(language) != null
        ) {
            //菜单国际化配置
            Map<String, String> menuMap = SystemUtil.constants.getI18nConfig().getMenu().get(language);
            permissionList.forEach(menu -> {
                if (menuMap.get(menu.getTitle()) != null) {
                    menu.setTitle(menuMap.get(menu.getTitle()));
                }
            });
        }
        List<Menu> rootList = filterPermissionRoot(permissionList);
        filterPermissionVO(rootList, permissionList);
        return rootList;
    }

    /**
     * 获取所有的权限树
     */
    public List<Menu> selectMenuList(String systemCode) {
        List<Menu> permissionList = menuMapper.selectAllMenu(systemCode);
        /*筛选出根目录*/
        List<Menu> rootList = filterPermissionRoot(permissionList);
        filterPermissionVO(rootList, permissionList);
        return rootList;
    }

    /**
     * 筛选出根目录
     */
    List<Menu> filterPermissionRoot(List<Menu> list) {
        List<Menu> rootList = list.stream().filter(item -> PermissionType.ROOT.getCode().equals(item.getType())).collect(Collectors.toList());
        return rootList.stream().map(item -> {
            Menu permissionVO = BeanUtil.toBean(item, Menu.class);
            permissionVO.setChildren(new ArrayList<>());
            return permissionVO;
        }).collect(Collectors.toList());
    }

    /**
     * 递归筛选出子目录
     */
    void filterPermissionVO(List<Menu> parentList, List<Menu> list) {
        List<Menu> childList = new ArrayList<>();
        /*先筛选出当前目录列表的所有子目录*/
        parentList.forEach(parent -> list.forEach(child -> {
            if (parent.getId().equals(child.getParentId())) {
                Menu permissionVO = BeanUtil.toBean(child, Menu.class);
                permissionVO.setChildren(new ArrayList<>());
                childList.add(permissionVO);
            }
        }));
        /*再筛选出所有子目录的下一级子目录*/
        if (childList.size() > 0) {
            filterPermissionVO(childList, list);
            Collections.sort(childList);
        }
        /*将子目录对应上一级目录*/
        parentList.forEach(parent -> childList.forEach(child -> {
            if (parent.getId().equals(child.getParentId())) {
                parent.getChildren().add(child);
            }
        }));
    }
}
