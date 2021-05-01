package com.noahpay.pay.platform.system.controller;

import com.kalvan.admin.dict.CacheUtil;
import com.kalvan.client.context.RequestContextHolder;
import com.kalvan.client.model.WebResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公用接口
 *
 * @author kalvan
 **/
@RestController
@RequestMapping("common")
public class CommonController {

    @GetMapping("getDictValue/{dictType}")
    public WebResponse getDictValue(@PathVariable("dictType") String dictType) {
        return WebResponse.buildSuccess().putData(CacheUtil.cache.getDictMap(RequestContextHolder.getContext().getLanguage(), dictType));
    }

}
