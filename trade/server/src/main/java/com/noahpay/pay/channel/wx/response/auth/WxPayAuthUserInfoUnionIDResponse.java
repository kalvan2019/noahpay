package com.noahpay.pay.channel.wx.response.auth;

import com.noahpay.pay.channel.wx.response.WxPayAuthResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpayauth userinfo UnionID response
 */
@Getter
@Setter
public class WxPayAuthUserInfoUnionIDResponse extends WxPayAuthResponse {

    private static final long serialVersionUID = 5822703842125391259L;

    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
     */
    private String subscribe;
    /**
     * 用户的唯一标识
     */
    private String openid;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private int sex;
    /**
     * 用户个人资料填写的省份
     */
    private String province;
    /**
     * 普通用户个人资料填写的城市
     */
    private String city;
    /**
     * 国家，如中国为CN
     */
    private String country;
    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效
     */
    private String headimgurl;
    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    private int subscribeTime;
    /**
     * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     */
    private String remark;
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
     */
    private String unionid;
    /**
     * 用户被打上的标签ID列表
     */
    private String tagidList;
    /**
     * 返回用户关注的渠道来源 <br/>
     * ADD_SCENE_SEARCH 公众号搜索 <br/>
     * ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移 <br/>
     * ADD_SCENE_PROFILE_CARD 名片分享 <br/>
     * ADD_SCENE_QR_CODE 扫描二维码 <br/>
     * ADD_SCENEPROFILE LINK 图文页内名称点击 <br/>
     * ADD_SCENE_PROFILE_ITEM 图文页右上角菜单 <br/>
     * ADD_SCENE_PAID 支付后关注 <br/>
     * ADD_SCENE_OTHERS 其他
     */
    private String subscribeScene;
    /**
     * 二维码扫码场景（开发者自定义）
     */
    private String qrScene;
    /**
     * 二维码扫码场景描述（开发者自定义）
     */
    private String qrSceneStr;

}
