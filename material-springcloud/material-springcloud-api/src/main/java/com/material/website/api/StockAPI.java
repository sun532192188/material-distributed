package com.material.website.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.StockDto;
import com.material.website.system.Pager;

/**
 * 库存客户端申明
 * @author sunxiaorong
 *
 */
@RequestMapping("/StockAPI")
public interface StockAPI {


	/**
	 * 查询库存信息(分页)
	 * @param stockArgs
	 * @return
	 */
	@RequestMapping(value="/queryStockPager",method=RequestMethod.GET)
	public Pager<StockDto> queryStockPager(@RequestParam Map<String, Object>map);
	
	
	/**
	 * 查询库存商品列表
	 * @param stockArgs
	 * @return
	 */
	@RequestMapping(value="/queryStockList",method=RequestMethod.GET)
	public List<StockDto> queryStockList(@RequestParam Map<String, Object>map);
}
