package com.material.website;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import com.material.website.web.interceptor.SpringContextUtil;

@SuppressWarnings("deprecation")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ServletComponentScan  //扫描servlet(这里主要用来扫描过滤器)
//返回jsp页面必须继承SpringBootServletInitializer类重写里面的方法
public class MaterialWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws IOException {
		 ApplicationContext app = SpringApplication.run(MaterialWebApplication.class, args);
		 SpringContextUtil.setApplicationContext(app);
	}
	
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MaterialWebApplication.class);
    }
}
