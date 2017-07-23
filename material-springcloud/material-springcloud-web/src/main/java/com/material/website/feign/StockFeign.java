package com.material.website.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.material.website.api.StockAPI;
import com.material.website.feign.config.FeignConfiguration;

/**
 * 库存客户端申明
 * @author sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface StockFeign extends StockAPI{

}
