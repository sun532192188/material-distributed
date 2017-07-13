package com.material.website.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.material.website.args.StatisUseAlloctArgs;
import com.material.website.args.UseAlloctAddArgs;
import com.material.website.args.UseAlloctQueryArgs;
import com.material.website.dto.GoodsInstallDto;
import com.material.website.dto.StatisUseAlloctDto;
import com.material.website.dto.UseAlloctDto;
import com.material.website.entity.UseAlloct;
import com.material.website.feign.config.FeignConfiguration;
import com.material.website.system.Pager;

import feign.Param;
import feign.RequestLine;

/**
 * 领用/调拨客户端申明
 * @author sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface UseAlloctFeign {

	/**
	 * 查询领用信息分页
	 * 
	 * @param queryArgs
	 * @return
	 */
	@RequestMapping(value="/queryDepartUsePager",method=RequestMethod.POST)
	public Pager<UseAlloctDto> queryDepartUsePager(@RequestBody UseAlloctQueryArgs queryArgs);

	/**
	 * 添加领用/调拨 
	 * @param addArgs
	 */
	@RequestMapping(value="/addUseAlloct",method=RequestMethod.POST)
	public Map<String, Object> addUseAlloct(@RequestBody UseAlloctAddArgs addArgs);
	/**
	 * 根据类型查询
	 * @param type
	 * @return
	 */
	@RequestLine("GET /queryUseAlloct/{type}")
	public Integer queryUseAlloct(@Param("type") Integer type);
	
	/**
	 * 根据编号查询商品
	 * @param useAlloctId
	 * @return
	 */
	@RequestLine("GET /queryGoodsList/{useAlloctId}")
	public List<GoodsInstallDto> queryGoodsList(@Param("useAlloctId") Integer useAlloctId);
	
	/**
	 * 统计库存出库分页
	 * @param queryArgs
	 * @return
	 */
	@RequestMapping(value="/statisUseAlloctPager",method=RequestMethod.POST)
	public Pager<StatisUseAlloctDto> statisUseAlloctPager(@RequestBody StatisUseAlloctArgs queryArgs);
	
	/**
	 * 更新初始化
	 * @param useAlloctId
	 * @return
	 */
	@RequestLine("GET /updateUseAlloctInit/{useAlloctId}")
	public Map<String, Object> updateUseAlloctInit(@Param("useAlloctId") Integer useAlloctId);
	
	/**
	 * 修改物资 调拨/领用信息
	 * @param addArgs
	 * @return
	 */
	@RequestMapping(value="/updateUseAlloct",method=RequestMethod.POST)
	public boolean updateUseAlloct(@RequestBody UseAlloctAddArgs updateArgs);
	
	/**
	 * 根据物资编号查询调拨/领用信息
	 * @param operatNo
	 * @return
	 */
	@RequestLine("GET /queryUseAlloctNo/{operatNo}")
	public UseAlloct queryUseAlloctNo(@Param("operatNo") String operatNo);
	
	/**
	 * 将调拨/领用物资入账
	 * @param useAlloctId
	 * @return
	 */
	@RequestLine("GET /addLockUseAlloct/{useAlloctId}")
	public boolean addLockUseAlloct(@Param("useAlloctId")  Integer useAlloctId);
}
