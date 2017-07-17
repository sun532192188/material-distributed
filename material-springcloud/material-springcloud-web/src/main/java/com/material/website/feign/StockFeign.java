package com.material.website.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.StockDto;
import com.material.website.feign.config.FeignConfiguration;
import com.material.website.system.Pager;

import feign.RequestLine;

/**
 * 库存客户端申明
 * @author sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface StockFeign {


	/**
	 * 查询库存信息(分页)
	 * @param stockArgs
	 * @return
	 */
	@RequestLine("GET /queryStockPager")
	public Pager<StockDto> queryStockPager(@RequestParam Map<String, Object>map);
	
	
	/**
	 * 查询库存商品列表
	 * @param stockArgs
	 * @return
	 */
	@RequestLine("GET /queryStockList")
	public List<StockDto> queryStockList(@RequestParam Map<String, Object>map);
}
