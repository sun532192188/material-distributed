package com.material.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import com.material.website.dao.BaseDao;

@SpringBootApplication
//该注解为启用eureka服务发现
@EnableDiscoveryClient
//该注解为启动服务扫描包  相当于spring配置文件中的<context:component-scan base-package="com.material.website" />
@ComponentScan({"com.material.website"})
@EnableJpaRepositories(repositoryBaseClass = BaseDao.class)
public class Application {
   
	public static void main( String[] args ){
        SpringApplication.run(Application.class, args);
    }
	
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

}
