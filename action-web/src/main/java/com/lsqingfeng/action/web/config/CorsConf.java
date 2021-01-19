package com.lsqingfeng.action.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @className: CorsConf
 * @description:
 * @author: sh.Liu
 * @date: 2021-01-19 17:03
 */
@Configuration
public class CorsConf implements WebMvcConfigurer {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许任何域名使用
        corsConfiguration.addAllowedOrigin("*");
        // 允许任何头
        corsConfiguration.addAllowedHeader("*");
        // 允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod("*");
        // 允许跨域带上cookies
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 解决请求经过的先后顺序问题，请求会先进入到自定义拦截器中，而不是进入Mapping映射中，所以返回的头信息中并没有配置的跨域信息，浏览器就会报跨域异常
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
