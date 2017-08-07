package com.material.website.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
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
        registry.addInterceptor(new ManagerAuthInterceptor()).addPathPatterns("/admin/**");  
        super.addInterceptors(registry);  
    } 
	
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry ) {
        registry.addViewController( "/" ).setViewName( "forward:/index.jsp" );
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE );
        super.addViewControllers( registry );
    } 
	
	
	/*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/**").addResourceLocations("classpath:/");
        super.addResourceHandlers(registry);
    }*/
}
