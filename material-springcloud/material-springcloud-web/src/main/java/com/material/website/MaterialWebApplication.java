package com.material.website;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MaterialWebApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MaterialWebApplication.class, args);
	}
}
