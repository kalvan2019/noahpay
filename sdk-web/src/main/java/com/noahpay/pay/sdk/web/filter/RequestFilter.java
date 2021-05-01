package com.noahpay.pay.sdk.web.filter;

import com.noahpay.pay.sdk.config.ProductConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;


/**
 * 扩展支持request 修改
 *
 * @author chenliang
 */
@Component
@Slf4j
public class RequestFilter implements Filter {
    @Resource
    ProductConfig productConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init RequestFilter...");
    }

    @Override
    public void destroy() {
        log.info("destroy RequestFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute("productConfig", productConfig);
        chain.doFilter(request, response);
    }
}