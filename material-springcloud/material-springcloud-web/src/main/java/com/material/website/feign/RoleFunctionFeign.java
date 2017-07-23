package com.material.website.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.material.website.api.RoleFunctionAPI;
import com.material.website.feign.config.FeignConfiguration;

/**
 * 角色功能业务接口
 * @author sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface RoleFunctionFeign extends RoleFunctionAPI{
	
}
