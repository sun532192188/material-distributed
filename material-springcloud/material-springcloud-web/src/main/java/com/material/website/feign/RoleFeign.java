package com.material.website.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.material.website.api.RoleAPI;
import com.material.website.feign.config.FeignConfiguration;

@FeignClient(name="material-springcloud-service")
public interface RoleFeign extends RoleAPI{
}
