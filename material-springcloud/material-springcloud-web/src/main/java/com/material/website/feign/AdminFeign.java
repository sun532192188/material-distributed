package com.material.website.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.material.website.api.AdminAPI;
import com.material.website.feign.config.FeignConfiguration;

/**
 * 用户申明式客户端
 * @author Sunxiaorong
 *
 */
//@FeignClient(name = "ea")意为通知Feign在调用该接口方法时要向Eureka中查询名为ea的服务，从而得到服务URL。
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface AdminFeign extends AdminAPI{
   
}
