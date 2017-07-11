/*package com.material.website.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

*//**
 * springboot 线程池配置类
 * 
 * @author Sunxiaorong
 *locations 表示本地文件
 *prefix 标识配置文件中的前缀   比如:spring.task.pool.corePoolSize=20
 *//*

@ConfigurationProperties(locations = "classpath:springThreadPool.properties",prefix = "spring.task.pool",ignoreUnknownFields = false) // 该注解的locations已经被启用，现在只要是在环境中，都会优先加载  
public class TaskThreadPoolConfig {

	*//**
	 * 线程池维护线程的最少数量
	 *//*
	private int corePoolSize;

	*//**
	 * 线程池维护线程的最大数量
	 *//*
	private int maxPoolSize;
    
	*//**
	 * 线程池维护线程所允许的空闲时间
	 *//*
	private int keepAliveSeconds;
	
	*//**
	 * 线程池所使用的缓冲队列
	 *//*
	private int queueCapacity;

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getKeepAliveSeconds() {
		return keepAliveSeconds;
	}

	public void setKeepAliveSeconds(int keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}

	public int getQueueCapacity() {
		return queueCapacity;
	}

	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}
}
*/