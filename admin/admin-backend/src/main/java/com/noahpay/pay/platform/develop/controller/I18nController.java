package com.noahpay.pay.platform.develop.controller;


import com.kalvan.client.model.Response;
import com.noahpay.pay.platform.system.service.AuthService;
import com.noahpay.pay.commons.db.platform.mapper.DictMapper;
import com.noahpay.pay.commons.db.platform.model.Dict;
import com.noahpay.pay.commons.db.platform.model.Menu;
import com.kalvan.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据字典配置Controller
 *
 * @author kalvan
 * @date 2020-08-23 15:07:36
 */
@Validated
@RestController
@RequestMapping("platform/develop/i18n")
@Slf4j
public class I18nController extends BaseController {
    @Resource
    DictMapper dictMapper;
    @Resource
    private AuthService authService;

    @PostMapping("validate1")
    public Response validate1(@Valid Response request) throws Exception {
        return Response.buildSuccess();
    }

    @PostMapping("validate2")
    public Response validate2(@Valid @RequestBody Response request) throws Exception {
        return Response.buildSuccess();
    }

    @PostMapping("validate3")
    public Response validate3(@NotBlank(message = "订单号不能为空") String orderId) throws Exception {
        return Response.buildSuccess();
    }

    /**
     * 查找菜单国际化配置
     * 配置文件 i18n-menu.yaml
     * 数据库查询
     */
    @PostMapping("menu")
    public void menu(HttpServletResponse response) throws Exception {
        List<Menu> list = authService.selectMenuList(null);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("i18n:").append("\n");
        stringBuilder.append("  menu:").append("\n");
        stringBuilder.append("      en-US:").append("\n");
        list.forEach(menu -> {
            stringBuilder.append("       '[").append(menu.getTitle()).append("]': ").append(menu.getTitle()).append("\n");
            if (menu.getChildren() != null) {
                menu.getChildren().forEach(menuChildren -> {
                    stringBuilder.append("       '[").append(menuChildren.getTitle()).append("]': ").append(menuChildren.getTitle()).append("\n");
                });
            }
        });
        //文件生成和下载
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=i18n-menu.yaml");
        InputStream is = new ByteArrayInputStream(stringBuilder.toString().getBytes());
        ServletOutputStream os = response.getOutputStream();
        int len = 0;
        byte[] flush = new byte[1024];
        while ((len = is.read(flush)) != -1) {
            os.write(flush, 0, len);
        }
        os.flush();
    }

    /**
     * 查找数据字典需要配置国际化
     * 配置文件 i18n-dict.yaml
     * 数据库查询
     */
    @PostMapping("dict")
    public void dict(HttpServletResponse response) throws Exception {
        List<Dict> dictTypeList = dictMapper.selectAllType();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("i18n:").append("\n");
        stringBuilder.append("  dict:").append("\n");
        stringBuilder.append("      en-US:").append("\n");
        dictTypeList.forEach(dictType -> {
            stringBuilder.append("          ").append(dictType.getDictType()).append(":").append("\n");
            List<Dict> dictList = dictMapper.selectDictByType(dictType.getDictType());
            dictList.forEach(dict -> {
                stringBuilder.append("              '[").append(dict.getDictKey()).append("]': ").append(dict.getDictValue()).append("\n");
            });
        });
        //文件生成和下载
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=i18n-dict.yaml");
        InputStream is = new ByteArrayInputStream(stringBuilder.toString().getBytes());
        ServletOutputStream os = response.getOutputStream();
        int len = 0;
        byte[] flush = new byte[1024];
        while ((len = is.read(flush)) != -1) {
            os.write(flush, 0, len);
        }
        os.flush();
    }

    /**
     * 查找返回码描述模板需要进行国际化配置
     * 配置文件 i18n-code.yaml
     * 代码查找关键字 new ReturnCode
     */
    @PostMapping("code")
    public void codeTemplate(HttpServletResponse response) throws Exception {
        String key = "new ReturnCode";
        //TODO 目录页面输入
        File fileRoot = new File("/Users/chenliang/dev/workspace/spring_thunder/hisun-pay/");
        Iterator<File> iterator = FileUtils.iterateFiles(fileRoot, new String[]{"java"}, true);
        Set<String> codeList = new TreeSet<>();
        String ignoreFile = this.getClass().getSimpleName() + ".java";
        while (iterator.hasNext()) {
            File file = iterator.next();
            if (file.getName().equals(ignoreFile)) {
                continue;
            }
            List<String> list = FileUtils.readLines(file);
            list.forEach(line -> {
                if (!line.trim().startsWith("//")) {
                    //非注释行
                    int i = line.indexOf(key);
                    if (i > 0) {
                        String code = line.substring(i);
                        String reg = "new ReturnCode\\((.*?)\\)";
                        Pattern pattern = Pattern.compile(reg);
                        Matcher m = pattern.matcher(code);
                        if (m.find()) {
                            String content = m.group(1);
                            String[] codeInfo = content.split(",");
                            if (codeInfo.length == 2) {
                                codeList.add("'[" + codeInfo[0].trim().replace("\"", "") + "]': " + codeInfo[1].trim().replace("\"", ""));
                            } else {
                                //未匹配则使用
                                codeList.add(line.substring(i));
                            }
                        }
                    }
                }
            });
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("i18n:").append("\n");
        stringBuilder.append("  code:").append("\n");
        stringBuilder.append("      en-US:").append("\n");
        codeList.forEach(code -> {
            stringBuilder.append("        ").append(code).append("\n");
        });
        //文件生成和下载
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=i18n-code.yaml");
        InputStream is = new ByteArrayInputStream(stringBuilder.toString().getBytes());
        ServletOutputStream os = response.getOutputStream();
        int len = 0;
        byte[] flush = new byte[1024];
        while ((len = is.read(flush)) != -1) {
            os.write(flush, 0, len);
        }
        os.flush();
    }

    /**
     * 查找描述占位填充动态描述进行国际化配置
     * 配置文件 i18n-message.yaml
     * 代码查找关键字 .formatMessage
     */
    @PostMapping("message")
    public void message(HttpServletResponse response) throws Exception {
        String key = ".formatMessage";
        //TODO 目录页面输入
        File fileRoot = new File("/Users/chenliang/dev/workspace/spring_thunder/hisun-pay/");
        Iterator<File> iterator = FileUtils.iterateFiles(fileRoot, new String[]{"java"}, true);
        Set<String> messageList = new TreeSet<>();
        String ignoreFile = this.getClass().getSimpleName() + ".java";
        while (iterator.hasNext()) {
            File file = iterator.next();
            if (file.getName().equals(ignoreFile)) {
                continue;
            }
            List<String> list = FileUtils.readLines(file);
            list.forEach(line -> {
                if (!line.trim().startsWith("//")) {
                    //非注释行
                    int i = line.indexOf(key);
                    if (i > 0) {
                        String message = line.substring(i);
                        String reg = ".formatMessage\\((.*?)\\)";
                        Pattern pattern = Pattern.compile(reg);
                        Matcher m = pattern.matcher(message);
                        if (m.find()) {
                            String content = m.group(1);
                            Arrays.stream(content.split(",")).forEach(item -> {
                                String str = item.trim().replace("\"", "");
                                messageList.add("'[" + str + "]': " + str);
                            });
                        }
                    }
                }
            });
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("i18n:").append("\n");
        stringBuilder.append("  message:").append("\n");
        stringBuilder.append("      en-US:").append("\n");
        messageList.forEach(message -> {
            stringBuilder.append("        ").append(message).append("\n");
        });
        //文件生成和下载
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=i18n-message.yaml");
        InputStream is = new ByteArrayInputStream(stringBuilder.toString().getBytes());
        ServletOutputStream os = response.getOutputStream();
        int len = 0;
        byte[] flush = new byte[1024];
        while ((len = is.read(flush)) != -1) {
            os.write(flush, 0, len);
        }
        os.flush();
    }

    /**
     * 查找注解校验动态描述进行国际化配置
     * 配置文件 i18n-message.yaml
     * 代码查找关键字 message = "
     */
    @PostMapping("validateMessage")
    public void validateMessage(HttpServletResponse response) throws Exception {
        String key = "message =";
        //TODO 目录页面输入
        File fileRoot = new File("/Users/chenliang/dev/workspace/spring_thunder/hisun-pay/");
        Iterator<File> iterator = FileUtils.iterateFiles(fileRoot, new String[]{"java"}, true);
        Set<String> messageList = new TreeSet<>();
        String ignoreFile = this.getClass().getSimpleName() + ".java";
        while (iterator.hasNext()) {
            File file = iterator.next();
            if (file.getName().equals(ignoreFile)) {
                continue;
            }
            List<String> list = FileUtils.readLines(file);
            list.forEach(line -> {
                if (!line.trim().startsWith("//") && line.trim().startsWith("@")) {
                    //非注释行,@开头的注解校验
                    int i = line.indexOf(key);
                    if (i > 0) {
                        String message = line.substring(i);
                        String reg = "message = \"(.*?)\"";
                        Pattern pattern = Pattern.compile(reg);
                        Matcher m = pattern.matcher(message);
                        if (m.find()) {
                            String content = m.group(1);
                            messageList.add("'[" + content + "]': " + content);
                        }
                    }
                }
            });
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("i18n:").append("\n");
        stringBuilder.append("  message:").append("\n");
        stringBuilder.append("      en-US:").append("\n");
        messageList.forEach(message -> {
            stringBuilder.append("        ").append(message).append("\n");
        });
        //文件生成和下载
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=i18n-message.yaml");
        InputStream is = new ByteArrayInputStream(stringBuilder.toString().getBytes());
        ServletOutputStream os = response.getOutputStream();
        int len = 0;
        byte[] flush = new byte[1024];
        while ((len = is.read(flush)) != -1) {
            os.write(flush, 0, len);
        }
        os.flush();
    }

    /**
     * 查找注解校验动态描述进行国际化配置
     * 配置文件 language.js
     * 代码查找关键字 $t('')
     */
    @PostMapping("vue")
    public void vue(HttpServletResponse response) throws Exception {
        String key = "$t('";
        //TODO 目录页面输入
        File fileRoot = new File("/Users/chenliang/dev/workspace/spring_thunder/hisun-pay/admin/admin-frontend/src/views/");
        Iterator<File> iterator = FileUtils.iterateFiles(fileRoot, new String[]{"vue"}, true);
        Set<String> messageList = new TreeSet<>();
        String ignoreFile = this.getClass().getSimpleName() + ".vue";
        while (iterator.hasNext()) {
            File file = iterator.next();
            if (file.getName().equals(ignoreFile)) {
                continue;
            }
            List<String> list = FileUtils.readLines(file);
            list.forEach(line -> {
                if (!line.trim().startsWith("//")) {
                    //非注释行
                    int i = line.indexOf(key);
                    if (i > 0) {
                        String message = line.substring(i);
                        String reg = "\\$t\\('(.+)'\\)";
                        Pattern pattern = Pattern.compile(reg);
                        Matcher m = pattern.matcher(message);
                        if (m.find()) {
                            String content = m.group(1);
                            messageList.add("'" + content + "': " + "'" + content + "'");
                        }
                    }
                }
            });
        }
        StringBuilder stringBuilder = new StringBuilder();
        messageList.forEach(message -> {
            stringBuilder.append(message).append("\n");
        });
        //文件生成和下载
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=language.js");
        InputStream is = new ByteArrayInputStream(stringBuilder.toString().getBytes());
        ServletOutputStream os = response.getOutputStream();
        int len = 0;
        byte[] flush = new byte[1024];
        while ((len = is.read(flush)) != -1) {
            os.write(flush, 0, len);
        }
        os.flush();
    }
}
