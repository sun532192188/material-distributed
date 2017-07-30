package com.material.website.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 创建拦截器链
 * @author sunxiaorong
 *
 */
@Configuration 
public class ManagerAuthAdapter extends WebMvcConfigurerAdapter{

	@Override  
    public void addInterceptors(InterceptorRegistry registry) {  
        //众多的拦截器组成了一个拦截器链  
        /** 
         * 主要方法说明： 
         * addPathPatterns 用于添加拦截规则 
         * excludePathPatterns 用户排除拦截 
         */  
        registry.addInterceptor(new ManagerAuthInterceptor()).addPathPatterns("/**");  
        super.addInterceptors(registry);  
    }  
}
