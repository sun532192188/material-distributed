package com.material.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.material.website.entity.User;
import com.material.website.repository.UserRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	
	@SuppressWarnings("unused")
	@GetMapping("/simple/{id}")
	public User findById(@PathVariable Long id){
		User user  = this.userRepository.findOne(id);
		return this.userRepository.findOne(id);
	}
	
	/**
	 * 该方法返回当前微服务ip+端口
	 * @return
	 */
	@GetMapping("/eureka-instance")
	public String serviceUrl() {
	    InstanceInfo instance = eurekaClient.getNextServerFromEureka("MICROSERVICE-PROVIDER-USER", false);
	    return instance.getHomePageUrl();
	}
	
	/***
	 * 该方法返回该微服务的详细信息(json串)
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@GetMapping("/eureka-info")
	public ServiceInstance sohwInfo(){
		ServiceInstance localServiceInstance  = this.discoveryClient.getLocalServiceInstance();
		return localServiceInstance;
	}
	
}
