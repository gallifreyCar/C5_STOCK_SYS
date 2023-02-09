package com.Gallifrey.springboot00.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//全局配置类--配置我们的跨域请求
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//跨域的内容
                .allowedOriginPatterns("*")//允许的跨域的来源
                .allowedMethods("GET","POST","PUT","OPTIONS","DELETE")//允许的跨域方法
                .allowCredentials(true)//允许跨域携带信息
                .allowedHeaders("*") //允许头部设置
                .maxAge(3600);//最大响应时间
    }

}
