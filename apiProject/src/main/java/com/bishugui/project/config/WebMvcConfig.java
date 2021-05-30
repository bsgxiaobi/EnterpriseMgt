package com.bishugui.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //.allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "X-Token", "content-type")
                .allowedHeaders("*")  //服务器允许的请求头
                .allowedMethods("POST", "PUT", "GET", "OPTIONS", "DELETE")  //服务器允许的请求方法
                .allowCredentials(true)  //允许带 cookie 的跨域请求
                .allowedOriginPatterns("*")  //服务端允许哪些域请求资源
                .maxAge(3600);   //预检请求的缓存时间
    }
/*    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")  //所有请求都被拦截包括静态资源
                .excludePathPatterns("/","/login","/css/**","/fonts/**","/images/**",
                        "/js/**","/aa/**"); //放行的请求

//        registry.addInterceptor(redisUrlCountInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/","/login","/css/**","/fonts/**","/images/**",
//                        "/js/**","/aa/**");
    }*/
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**","/img/**").addResourceLocations("classpath:/static/**","classpath:/static/img/**");
    }*/
}
