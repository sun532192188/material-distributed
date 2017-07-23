package com.material.website.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.material.website.api.StorageAPI;
import com.material.website.feign.config.FeignConfiguration;

/**
 * 入库客户端申明
 * @author Sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface StorageFeign extends StorageAPI{

}
