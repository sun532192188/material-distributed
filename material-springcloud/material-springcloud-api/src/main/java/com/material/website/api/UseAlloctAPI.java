package com.material.website.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.args.UseAlloctAddArgs;
import com.material.website.dto.GoodsInstallDto;
import com.material.website.dto.StatisUseAlloctDto;
import com.material.website.dto.UseAlloctDto;
import com.material.website.entity.UseAlloct;
import com.material.website.system.Pager;


/**
 * 领用/调拨客户端申明
 * @author sunxiaorong
 *
 */
@RequestMapping("/UseAlloctAPI")
public interface UseAlloctAPI {

	/**
	 * 查询领用信息分页
	 * 
	 * @RequestParam queryArgs
	 * @return
	 */
	@RequestMapping(value="/queryDepartUsePager",method=RequestMethod.GET)
	public Pager<UseAlloctDto> queryDepartUsePager(@RequestParam Map<String, Object>map);

	/**
	 * 添加领用/调拨 
	 * @RequestParam addArgs
	 */
	@RequestMapping(value="/addUseAlloct",method=RequestMethod.GET)
	public Map<String, Object> addUseAlloct(@RequestBody UseAlloctAddArgs addArgs);
	/**
	 * 根据类型查询
	 * @RequestParam type
	 * @return
	 */
	@RequestMapping(value="/queryUseAlloct",method=RequestMethod.GET)
	public Integer queryUseAlloct(@RequestParam("type") Integer type);
	
	/**
	 * 根据编号查询商品
	 * @RequestParam useAlloctId
	 * @return
	 */
	@RequestMapping(value="/queryGoodsList",method=RequestMethod.GET)
	public List<GoodsInstallDto> queryGoodsList(@RequestParam("useAlloctId") Integer useAlloctId);
	
	/**
	 * 统计库存出库分页
	 * @RequestParam queryArgs
	 * @return
	 */
	@RequestMapping(value="/statisUseAlloctPager",method=RequestMethod.GET)
	public Pager<StatisUseAlloctDto> statisUseAlloctPager(@RequestParam Map<String, Object>map);
	
	/**
	 * 更新初始化
	 * @RequestParam useAlloctId
	 * @return
	 */
	@RequestMapping(value="/updateUseAlloctInit",method=RequestMethod.GET)
	public Map<String, Object> updateUseAlloctInit(@RequestParam("useAlloctId") Integer useAlloctId);
	
	/**
	 * 修改物资 调拨/领用信息
	 * @RequestParam addArgs
	 * @return
	 */
	@RequestMapping(value="/updateUseAlloct",method=RequestMethod.POST)
	public boolean updateUseAlloct(@RequestParam("json") String json);
	
	/**
	 * 根据物资编号查询调拨/领用信息
	 * @RequestParam operatNo
	 * @return
	 */
	@RequestMapping(value="/queryUseAlloctNo",method=RequestMethod.GET)
	public UseAlloct queryUseAlloctNo(@RequestParam("operatNo") String operatNo);
	
	/**
	 * 将调拨/领用物资入账
	 * @RequestParam useAlloctId
	 * @return
	 */
	@RequestMapping(value="/addLockUseAlloct",method=RequestMethod.POST)
	public boolean addLockUseAlloct(@RequestParam("useAlloctId")  Integer useAlloctId);
}
