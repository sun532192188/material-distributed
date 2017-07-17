package com.material.website.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * web应用自定义资源映射
 * @author Sunxiaorong
 *
 */
@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter{
   
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//可以多次使用 addResourceLocations 添加目录，优先级先添加的高于后添加的。
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/upload/");
        super.addResourceHandlers(registry);
    }
}
