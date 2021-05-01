package com.noahpay.pay.platform.system.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.AdminMapper;
import com.noahpay.pay.commons.db.platform.mapper.AdminRoleMapper;
import com.noahpay.pay.commons.db.platform.model.Admin;
import com.noahpay.pay.commons.db.platform.model.AdminRole;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 管理员Service实现类
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Service
public class AdminService extends BaseAuditService<Admin> {
    @Resource
    protected AdminMapper mapper;
    @Resource
    protected AdminRoleMapper adminRoleMapper;

    @Override
    public void processParams(Map<String, Object> params) {
        //  查询和下载的条件检查
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(Admin admin) {
        //  填充数据,选择字段进行检查，更新人，更新时间
        saveCheck(admin);
        String salt = RandomStringUtils.randomAlphanumeric(16);
        admin.setSalt(salt);
        admin.setPassword(DigestUtil.md5Hex(admin.getPassword() + salt));
        admin.setIsSystem(SwitchEnum.CLOSE.code);
        admin.setLoginCount(0);
        admin.setLoginErrorCount(0);
        admin.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(admin);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(Admin admin) {
        Admin adminDb = mapper.selectByUk(admin.getId());
        if (adminDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        if (adminDb.getIsSystem() == SwitchEnum.OPEN.code) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        saveCheck(admin);
        //  更新指定字段，更新人
        Admin adminNew = new Admin();
        adminNew.setId(admin.getId());
        adminNew.setUserCode(admin.getUserCode());
        adminNew.setUserName(admin.getUserName());
        String password = admin.getPassword();
        if (StringUtils.isNotBlank(password)) {
            String newPassword = DigestUtil.md5Hex(password.toLowerCase() + adminDb.getSalt());
            adminNew.setPassword(newPassword);
        }
        adminNew.setMobile(admin.getMobile());
        adminNew.setEmail(admin.getEmail());
        adminNew.setState(admin.getState());
        adminNew.setUpdateTime(DateUtil.date());
        adminNew.setRoleId(admin.getRoleId());
        return this.editDataAudit(adminDb, adminNew);
    }

    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int updatePwd(String userCode, String password) {
        Admin adminDb = mapper.selectByUserCode(userCode);
        if (adminDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        Admin adminNew = new Admin();
        adminNew.setId(adminDb.getId());
        adminNew.setPassword(DigestUtil.md5Hex(password + adminDb.getSalt()));
        return mapper.updateByUkSelective(adminNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            Admin adminDb = mapper.selectByUk(id);
            if (adminDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            if (SwitchEnum.OPEN.code == adminDb.getIsSystem()) {
                throw new BizException(AdminReturnCode.DATA_STATE_ERROR.formatMessage(adminDb.getUserCode()));
            }
            resultNum = resultNum + this.deleteDataAudit(adminDb);
        }
        return resultNum;
    }

    @Override
    public void auditingNotify(Admin oldObject, Admin newObject) {
        //审核通过后维护角色关联数据
        if (newObject != null) {
            Long adminId = newObject.getId();
            if (ArrayUtils.isNotEmpty(newObject.getRoleId())) {
                for (Long roleId : newObject.getRoleId()) {
                    saveAdminRole(adminId, roleId);
                }
            }
        } else {
            deleteAdminRole(oldObject.getId());
        }
    }

    /**
     * 清空用户角色
     */
    private void deleteAdminRole(Long adminId) {
        AdminRole adminRoleDelete = new AdminRole();
        adminRoleDelete.setAdminId(adminId);
        adminRoleMapper.delete(adminRoleDelete);
    }

    /**
     * 新增用户角色
     */
    private void saveAdminRole(Long adminId, Long roleId) {
        AdminRole adminRoleNew = new AdminRole();
        adminRoleNew.setAdminId(adminId);
        adminRoleNew.setRoleId(roleId);
        adminRoleMapper.insertSelective(adminRoleNew);
    }

    private void saveCheck(Admin admin) {
        Condition condition = new Condition(Admin.class);
        condition.createCriteria()
                .andCondition("user_code=", admin.getUserCode())
                .orCondition("mobile=", admin.getMobile())
                .orCondition("email=", admin.getEmail());
        if (mapper.selectCountByCondition(condition) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT.formatMessage(
                    String.format("用户号%s 手机号%s email%s", admin.getUserCode(), admin.getMobile(), admin.getEmail())));
        }
    }

}
