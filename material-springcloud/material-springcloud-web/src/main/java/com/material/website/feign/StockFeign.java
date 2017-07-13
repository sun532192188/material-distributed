package com.material.website.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.material.website.args.StockArgs;
import com.material.website.dto.StockDto;
import com.material.website.feign.config.FeignConfiguration;
import com.material.website.system.Pager;

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
	@RequestMapping(value="/queryStockPager",method=RequestMethod.POST)
	public Pager<StockDto> queryStockPager(@RequestBody StockArgs stockArgs);
	
	
	/**
	 * 查询库存商品列表
	 * @param stockArgs
	 * @return
	 */
	@RequestMapping(value="/queryStockList",method=RequestMethod.POST)
	public List<StockDto> queryStockList(@RequestBody StockArgs stockArgs);
}
