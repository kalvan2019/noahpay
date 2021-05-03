package com.noahpay.pay.channel.wx.util;

import com.alibaba.fastjson.JSONObject;
import com.noahpay.pay.channel.wx.enums.WxPayConstants;
import com.noahpay.pay.channel.wx.enums.WxpayJsApiType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

/**
 * wxpay工具类
 *
 * @version 1.3.0
 */
@Slf4j
@Getter
@Setter
public class WxpayUtils {

    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Random RANDOM = new SecureRandom();

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static WxpayHashMap xmlToMap(String strXML) throws Exception {
        WxpayHashMap data = new WxpayHashMap();
        DocumentBuilder documentBuilder = XmlUtils.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(strXML.getBytes(StandardCharsets.UTF_8));
        Document doc = documentBuilder.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int idx = 0; idx < nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String text = element.getTextContent();
                if (element.hasChildNodes() && element.getChildNodes().getLength() > 1) {
                    NodeList childNodeList = element.getChildNodes();
                    StringBuffer sb1 = new StringBuffer("[");
                    for (int i = 0; i < childNodeList.getLength(); i++) {
                        Node childNode = childNodeList.item(i);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element childElement = (Element) childNode;
                            if (!childElement.getNodeName().contains("#")) {
                                StringBuffer sb2 = new StringBuffer();
                                sb2.append("{\"" + childElement.getNodeName() + "\":");
                                if (childElement.hasChildNodes()) {
                                    NodeList childNodeList2 = childElement.getChildNodes();
                                    for (int j = 0; j < childNodeList2.getLength(); j++) {
                                        Node childNode2 = childNodeList2.item(j);
                                        if (childNode2.getNodeType() == Node.ELEMENT_NODE) {
                                            if (j == 0) {
                                                sb2.append("{");
                                            }
                                            Element childElement2 = (Element) childNode2;
                                            sb2.append("\"" + childElement2.getNodeName() + "\":\"" + childElement2.getTextContent() + "\"");
                                            if (j < childNodeList2.getLength() - 1) {
                                                sb2.append(",");
                                            }
                                        }
                                    }
                                    sb2.append("}");
                                }
                                sb2.append("}");
                                sb1.append(sb2);
                                if (i < childNodeList.getLength() - 1) {
                                    sb1.append(",");
                                }
                            }
                        }
                    }
                    sb1.append("]");
                    text = sb1.toString();
                }
                data.put(element.getNodeName(), text);
            }
        }
        stream.close();
        return data;
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, Object> data) {
        try {
            Document document = XmlUtils.newDocument();
            Element root = document.createElement("xml");
            document.appendChild(root);
            for (String key : data.keySet()) {
                String value = String.valueOf(data.get(key));
                if (value == null) {
                    value = "";
                }
                value = value.trim();
                Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, WxPayConstants.CHARSET_UTF8);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
            writer.close();
            return output;
        } catch (Exception e) {
            log.error("map to xml failed", e);
        }
        return null;
    }

    /**
     * 获取随机字符串 nonce_str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

    /**
     * map转换java对象
     *
     * @param map
     * @param beanClass
     * @return java对象
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T mapToObject(Map<?, ?> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        T obj = (T) beanClass.newInstance();
        Field[] childFields = obj.getClass().getDeclaredFields();
        Field[] superFields = obj.getClass().getSuperclass().getDeclaredFields();
        for (Field field : childFields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            // 处理字段为 packageInfo问题 取key为package的值
            field.set(obj, map.get(camelToUnderline(field.getName().replace("Info", ""))));
        }
        for (Field field : superFields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(camelToUnderline(field.getName())));
        }
        return obj;
    }
    public static final char UNDERLINE = '_';
    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param field 字段
     */
    public static String camelToUnderline(String field) {
        if (StringUtils.isEmpty(field)) {
            return "";
        }
        int len = field.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = field.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    /**
     * java bean 转化 map
     *
     * @param javaBean
     * @return map
     * @throws Exception
     */
    public static WxpayHashMap beanToMap(Object javaBean) throws Exception {
        WxpayHashMap hashMap = new WxpayHashMap();
        // 获取javaBean属性
        BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        if (propertyDescriptors != null && propertyDescriptors.length > 0) {
            String propertyName = null;
            Object propertyValue = null;
            for (PropertyDescriptor pd : propertyDescriptors) {
                propertyName = pd.getName();
                if (!"class".equals(propertyName)) {
                    Method readMethod = pd.getReadMethod();
                    propertyValue = readMethod.invoke(javaBean);
                    hashMap.put(propertyName, propertyValue);
                }
            }
        }

        return hashMap;
    }

    /**
     * json字串转Map
     *
     * @param jsonStr json字串
     * @return map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> jsonToMap(String jsonStr) {
        JSONObject data = JSONObject.parseObject(jsonStr);
        return JSONObject.toJavaObject(data, Map.class);
    }

    /**
     * json字串转MapObject
     *
     * @param jsonStr json字串
     * @return map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMapObject(String jsonStr) {
        JSONObject data = JSONObject.parseObject(jsonStr);
        return JSONObject.toJavaObject(data, Map.class);
    }

    /**
     * 字典序拼接参数字串
     *
     * @param appParams
     * @return
     */
    public static String getSortedParams(Map<String, String> appParams) {
        Set<String> keySet = appParams.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            String k = keyArray[i];
            // 参数值为空，则不参与签名
            if (appParams.get(k) != null && appParams.get(k).trim().length() > 0) {
                sb.append(k).append("=").append(appParams.get(k).trim());
                if (i < keyArray.length - 1) {
                    sb.append("&");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 字典序拼接参数字串
     *
     * @param appParams
     * @return
     */
    public static String getSortedParamsObject(Map<String, Object> appParams) {
        Set<String> keySet = appParams.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            String k = keyArray[i];
            // 参数值为空，则不参与签名
            if (appParams.get(k) != null) {
                sb.append(k).append("=").append(appParams.get(k));
                if (i < keyArray.length - 1) {
                    sb.append("&");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 解密退款通知加密数据
     *
     * @param data req_info
     * @param key  商户密钥
     * @return
     * @throws Exception
     */
    public static String decryptRefundNotifyData(String data, String key) throws Exception {
        return WxpayEncrypt.aesDecrypt(data, key);
    }

    /**
     * 获取JS接口列表
     *
     * @param jsApiType 类型
     * @return JS接口列表
     */
    public static List<String> getJsApiList(String jsApiType) {
        List<String> jsApiList = new ArrayList<>();
        if (WxpayJsApiType.share.name().equals(jsApiType)) {
            jsApiList.add("updateAppMessageShareData");
            jsApiList.add("updateTimelineShareData");
            jsApiList.add("onMenuShareWeibo");
            jsApiList.add("onMenuShareQZone");
        } else if (WxpayJsApiType.image.name().equals(jsApiType)) {
            jsApiList.add("chooseImage");
            jsApiList.add("previewImage");
            jsApiList.add("uploadImage");
            jsApiList.add("downloadImage");
            jsApiList.add("getLocalImgData");
        } else if (WxpayJsApiType.audio.name().equals(jsApiType)) {
            jsApiList.add("startRecord");
            jsApiList.add("stopRecord");
            jsApiList.add("onVoiceRecordEnd");
            jsApiList.add("playVoice");
            jsApiList.add("pauseVoice");
            jsApiList.add("stopVoice");
            jsApiList.add("onVoicePlayEnd");
            jsApiList.add("uploadVoice");
            jsApiList.add("downloadVoice");
        } else if (WxpayJsApiType.translate.name().equals(jsApiType)) {
            jsApiList.add("translateVoice");
        } else if (WxpayJsApiType.network.name().equals(jsApiType)) {
            jsApiList.add("getNetworkType");
        } else if (WxpayJsApiType.location.name().equals(jsApiType)) {
            jsApiList.add("openLocation");
            jsApiList.add("getLocation");
        } else if (WxpayJsApiType.ibeacon.name().equals(jsApiType)) {
            jsApiList.add("startSearchBeacons");
            jsApiList.add("stopSearchBeacons");
            jsApiList.add("onSearchBeacons");
        } else if (WxpayJsApiType.ui.name().equals(jsApiType)) {
            jsApiList.add("closeWindow");
            jsApiList.add("hideMenuItems");
            jsApiList.add("showMenuItems");
            jsApiList.add("hideAllNonBaseMenuItem");
            jsApiList.add("showAllNonBaseMenuItem");
        } else if (WxpayJsApiType.scan.name().equals(jsApiType)) {
            jsApiList.add("scanQRCode");
        } else if (WxpayJsApiType.shop.name().equals(jsApiType)) {
            jsApiList.add("openProductSpecificView");
        } else if (WxpayJsApiType.coupon.name().equals(jsApiType)) {
            jsApiList.add("chooseCard");
            jsApiList.add("addCard");
            jsApiList.add("openCard");
        } else if (WxpayJsApiType.pay.name().equals(jsApiType)) {
            jsApiList.add("chooseWXPay");
        } else if (WxpayJsApiType.address.name().equals(jsApiType)) {
            jsApiList.add("openAddress");
        }
        return jsApiList;
    }
}

