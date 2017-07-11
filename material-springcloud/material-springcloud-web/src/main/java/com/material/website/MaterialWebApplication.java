package com.material.website;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class MaterialWebApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MaterialWebApplication.class, args);
	}
}
