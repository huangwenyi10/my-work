package com.example.interceptor.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Ay on 2017/8/23.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {


    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/springdata/*");
    }


}
