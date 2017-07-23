package com.material.website.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.material.website.api.CategoryAPI;
import com.material.website.feign.config.FeignConfiguration;


@FeignClient(name="material-springcloud-service",configuration = FeignConfiguration.class)
public interface CategoryFeign extends CategoryAPI{

}
