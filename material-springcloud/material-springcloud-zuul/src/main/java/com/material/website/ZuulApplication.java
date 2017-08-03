package com.material.website;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

import com.material.website.filter.AccessFilter;

@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {
	

	public static void main(String[] args) {
		//SpringApplication.run(ZuulApplication.class, args);
		new SpringApplicationBuilder(ZuulApplication.class).web(true) .run(args);
	}
	
	/**
	 * zuul使用正则匹配请求
	 * 要使用该方法 只需将 微服务application:name 后加-v 如 material-service-v1
	 * 访问方式: 将被映射到路由“/ v1/ **”
	 * 如果请求路径不符合上述要求则按照默认路径
	 * @return
	 */
	@Bean
	public PatternServiceRouteMapper serviceRouteMapper() {
	    return new PatternServiceRouteMapper(
	        "(?<name>^.+)-(?<version>v.+$)",
	        "${version}/${name}");
	}
	
	/**
	 * 启动zuul过滤器
	 * @return
	 */
	@Bean
	public AccessFilter accessFilter(){
		return new AccessFilter();
	}
}
