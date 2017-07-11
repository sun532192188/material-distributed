package com.material.website;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceSimpleProviderUserApplication {

	public static void main(String[] args) throws IOException {
	/*	Properties propertites = new Properties();
		InputStream  in = MicroserviceSimpleProviderUserApplication.class.getClassLoader().getResourceAsStream("bootstrap.yml");
		propertites.load(in);
		SpringApplication application  = new SpringApplication(MicroserviceSimpleProviderUserApplication.class);
		application.setDefaultProperties(propertites);
	    application.run(args);*/
		SpringApplication.run(MicroserviceSimpleProviderUserApplication.class, args);
	}
}
