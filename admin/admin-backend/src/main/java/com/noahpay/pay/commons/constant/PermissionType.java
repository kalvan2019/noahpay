package com.noahpay.pay.commons.constant;

/**
 * 菜单资源类型枚举
 *
 * @author kalvan
 */
public enum PermissionType {
    /**
     * 菜单
     */
    ROOT("root", "目录"),
    CHILDREN("menu", "菜单"),
    FUNCTION("button", "按钮功能"),
    ;
    private String code;
    private String desc;

    PermissionType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
