package com.material.website.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.material.website.api.DepartmentAPI;
import com.material.website.feign.config.FeignConfiguration;

/**
 * 部门客户端申明
 * @author Sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface DepartmentFeign extends DepartmentAPI{

}
