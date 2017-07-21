package com.material.website.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.material.website.api.DepartmentCenterAPI;
import com.material.website.feign.config.FeignConfiguration;

/**
 * 部门中心客户端
 * 
 * @author sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface DepartmentCenterFeign extends DepartmentCenterAPI{

}
