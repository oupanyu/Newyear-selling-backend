package com.gl18.webmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WebmarketApplication {

    public static DBConfigReader config;

    public static void main(String[] args) throws ClassNotFoundException {

        config = new DBConfigReader();
        Class.forName("com.mysql.cj.jdbc.Driver");
        SpringApplication.run(WebmarketApplication.class, args);
    }


    //springboot 2.0以上的方式
    @Configuration
    public static class WebMvcConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    //是否发送Cookie
                    .allowCredentials(true)
                    //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
                    .allowedOriginPatterns("*")
                    //放行哪些请求方式
                    .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                    //.allowedMethods("*") //或者放行全部
                    //放行哪些原始请求头部信息
                    .allowedHeaders("*")
                    //暴露哪些原始请求头部信息
                    .exposedHeaders("*");
        }
    }
}
