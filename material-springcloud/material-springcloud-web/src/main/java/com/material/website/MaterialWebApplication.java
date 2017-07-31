package com.material.website;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import com.material.website.web.interceptor.SpringContextUtil;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ServletComponentScan  //扫描servlet(这里主要用来扫描过滤器)
public class MaterialWebApplication {

	public static void main(String[] args) throws IOException {
		 ApplicationContext app = SpringApplication.run(MaterialWebApplication.class, args);
		 SpringContextUtil.setApplicationContext(app);
	}
}
