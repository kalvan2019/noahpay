package com.noahpay.pay.commons.web;

import com.noahpay.pay.commons.web.interceptor.DecryptInterceptor;
import com.noahpay.pay.commons.web.interceptor.PermissionInterceptor;
import com.noahpay.pay.commons.web.interceptor.TokenInterceptor;
import com.kalvan.web.servlet.interceptor.CallInterfaceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.annotation.Resource;

/**
 * @author chenliang
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    String[] excludes = new String[]{"/images/**", "/js/**", "/css/**", "/error", "/admin-ui/**"};

    @Resource
    TokenInterceptor tokenInterceptor;
    @Resource
    PermissionInterceptor permissionInterceptor;
    @Resource
    DecryptInterceptor decryptInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("init CallInterfaceInterceptor...");
        registry.addInterceptor(new CallInterfaceInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);
        log.info("init TokenInterceptor...");
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns(excludes);
        log.info("init PermissionInterceptor...");
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/**").excludePathPatterns(excludes);
        log.info("init DecryptInterceptor...");
        registry.addInterceptor(decryptInterceptor).addPathPatterns("/**").excludePathPatterns(excludes);
    }

    /**
     * 解决vue-router路由的路径无法正常解析
     * 路径加上统一的前缀“/admin-ui”(自定义)，然后在springboot项目中自定义过滤器，拦截请求转发到“/index.html”
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return factory -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/admin-ui/index.html");
            factory.addErrorPages(error404Page);
        };
    }
    /**
     * springboot不拦截静态资源需配置
     *
     * @param registry registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setCookieMaxAge(3600);
        slr.setCookieName("Language");
        return slr;
    }

    /**
     * 跨域支持
     */
    private CorsConfiguration addCorsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", addCorsConfig());
        return new CorsFilter(source);
    }
}
