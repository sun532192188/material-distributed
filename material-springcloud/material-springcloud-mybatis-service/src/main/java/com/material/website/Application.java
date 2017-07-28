package com.material.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ServletComponentScan 
//该注解为启用eureka服务发现
//@EnableDiscoveryClient
//该注解为启动服务扫描包  相当于spring配置文件中的<context:component-scan base-package="com.material.website" />
@ComponentScan({"com.material.website","com.material.website.config"})
@EnableAutoConfiguration  
//开启springboot的servlet扫描功能
public class Application {
   
	public static void main( String[] args ){
        SpringApplication.run(Application.class, args);
    }
}
