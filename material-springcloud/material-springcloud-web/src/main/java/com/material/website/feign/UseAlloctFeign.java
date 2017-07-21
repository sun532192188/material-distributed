package com.material.website.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.material.website.api.UseAlloctAPI;
import com.material.website.feign.config.FeignConfiguration;

/**
 * 领用/调拨客户端申明
 * @author sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface UseAlloctFeign extends UseAlloctAPI{

}
