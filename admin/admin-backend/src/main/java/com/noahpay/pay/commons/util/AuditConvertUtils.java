package com.noahpay.pay.commons.util;//package com.kalvan.admin.system.util;
//
//
//import com.alibaba.excel.annotation.ExcelProperty;
//import com.alibaba.fastjson.JSONObject;
//import com.kalvan.sensitive.utils.SensitiveInfoUtils;
//import com.kalvan.web.dict.CacheUtil;
//import com.kalvan.web.excel.annotation.Dict;
//import com.kalvan.web.excel.annotation.NumberConvert;
//import lombok.extern.slf4j.Slf4j;
//
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
///***
// * 审核信息转换
// */
//@Slf4j
//public class AuditConvertUtils {
//
//    /**
//     * 中文转换
//     *
//     * @param field
//     * @param name
//     * @return
//     */
//    private static String convert(Field field, String name) {
//        if (field != null) {
//            //判断字段是否含有注解
//            if (field.getAnnotation(ExcelProperty.class) != null) {
//                //获取注解属性
//                ExcelProperty property = field.getAnnotation(ExcelProperty.class);
//                //设置可见性
//                field.setAccessible(true);
//                //获取对应的中文
//                String[] value = property.value();
//                //相等则返回
//                if (name.equals(field.getName())) {
//                    return value[0];
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 获取字典值
//     *
//     * @param dictKey
//     * @return
//     */
//    public static Map dictValue(String dictKey) {
//        Map<String, DictDto> cacheMap = CacheUtil.getCache().getDictMap(dictKey);
//        if (cacheMap != null) {
//            return cacheMap;
//        }
//        return null;
//    }
//
//    public static Object processObject(Object javaBean, boolean hasPlaintextPermission) {
//        AuditConvertUtils.auditTempConvert(javaBean);
//        return hasPlaintextPermission ? javaBean : SensitiveInfoUtils.processObject(javaBean);
//    }
//
//    /**
//     * 转换成AuditTemp
//     *
//     * @param object
//     */
//    public static void auditTempConvert(Object object) {
//        try {
//            if (object == null) {
//                return;
//            }
//            Field[] fields = object.getClass().getDeclaredFields();
//            for (Field field : fields) {
//                String auditTemp = "auditTemp";
//                //如果存auditTemp审核字段，则转换成HTML显示
//                if (auditTemp.equals(field.getName())) {
//                    //设置可见性
//                    field.setAccessible(true);
//                    JSONObject dataJson = JSONObject.parseObject(String.valueOf(field.get(object)));
//                    //转换成HTML
//                    String html = toHtml(object, dataJson);
//                    field.set(object, html);
//                }
//            }
//        } catch (Exception e) {
//            log.error("auditConvert error:", e);
//        }
//    }
//
//    /**
//     * 将待审转换成Table
//     *
//     * @param object   原实体类
//     * @param dataJson 待审信息
//     * @return
//     * @throws IllegalAccessException
//     */
//    public static String toHtml(Object object, JSONObject dataJson) throws IllegalAccessException {
//        if (object == null || dataJson == null) {
//            return "";
//        }
//        //获取实体类中所有字段
//        Field[] fields = object.getClass().getDeclaredFields();
//        //建立JSON集合
//        List<JSONObject> dataJsonList = new ArrayList<>();
//        //遍历字段
//        for (Field field : fields) {
//            //设置可见性
//            field.setAccessible(true);
//            //判断是否等于空
//            if (!field.getName().equals("serialVersionUID")) {
//                //遍历json
//                Iterator<String> iterator = dataJson.keySet().iterator();
//                while (iterator.hasNext()) {
//                    String key = iterator.next();
//                    if (field.getName().equals(key)) {
//                        JSONObject data = new JSONObject();
//                        //获取字典
//                        Dict dict = field.getAnnotation(Dict.class);
//                        String character = String.valueOf(field.get(object));
//                        String newCharacter = String.valueOf(dataJson.get(key));
//                        //判断原有数据和新数据是否相等，否则不显示
//                        if (!character.equals(newCharacter)) {
//                            //转换成中文
//                            data.put("name", convert(field, key));
//                            if (dict != null) {
//                                String dictKey = dict.value();
//                                Map<String, DictDto> cacheMap = dictValue(dictKey);
//                                if (cacheMap != null) {
//                                    data.put("character", cacheMap.get(character).getDictValue());
//                                    data.put("newCharacter", cacheMap.get(newCharacter).getDictValue());
//                                } else {
//                                    data.put("character", character);
//                                    data.put("newCharacter", newCharacter);
//                                }
//                            } else {
//                                //金额格式化
//                                NumberConvert numberConvert = field.getAnnotation(NumberConvert.class);
//                                if (numberConvert != null) {
//                                    Long number = numberConvert.value();
//                                    character = String.valueOf(new BigDecimal(character).divide(new BigDecimal(number)).setScale(2));
//                                    newCharacter = String.valueOf(new BigDecimal(newCharacter).divide(new BigDecimal(number)).setScale(2));
//                                }
//                                data.put("character", character);
//                                data.put("newCharacter", newCharacter);
//                            }
//                            dataJsonList.add(data);
//                        }
//                    }
//                }
//            }
//        }
//        if (dataJsonList != null && dataJsonList.size() > 0) {
//            return html(dataJsonList);
//        }
//        return "";
//    }
//
//    /**
//     * 将待审信息转换成HTML格式
//     *
//     * @param dataJsonList
//     * @return
//     */
//    private static String html(List<JSONObject> dataJsonList) {
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("  <table>");
//        stringBuffer.append("   <tr style=\"font-weight: bold;\">");
//        stringBuffer.append("      <td>审核字段</td>");
//        stringBuffer.append("      <td>原有信息</td>");
//        stringBuffer.append("      <td>修改信息</td>");
//        stringBuffer.append("   </tr>");
//        for (JSONObject data : dataJsonList) {
//            String name = String.valueOf(data.get("name"));
//            String character = String.valueOf(data.get("character"));
//            String newCharacter = String.valueOf(data.get("newCharacter"));
//            int maxLength = 32;
//            if (character != null && character.length() > maxLength) {
//                character = character.substring(0, maxLength);
//                character = character + "...";
//            }
//            if (newCharacter != null && newCharacter.length() > maxLength) {
//                newCharacter = newCharacter.substring(0, maxLength);
//                newCharacter = newCharacter + "...";
//            }
//            if (name != null && name.length() > maxLength) {
//                name = name.substring(0, maxLength);
//                name = name + "...";
//            }
//            stringBuffer.append("   <tr>");
//            stringBuffer.append("      <td><a href='#' title='" + data.get("name") + "'>").append(name).append("</a></td>");
//            stringBuffer.append("      <td><a href='#' title='" + data.get("character") + "'>").append(character).append("</a></td>");
//            stringBuffer.append("      <td><a href='#' title='" + data.get("newCharacter") + "'>").append(newCharacter).append("</a></td>");
//            stringBuffer.append("   </tr>");
//        }
//        stringBuffer.append("  </table>");
//        return stringBuffer.toString();
//    }
//
//}
