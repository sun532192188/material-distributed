package com.material.website.api;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.DeparPlanDto;
import com.material.website.dto.DepartStockDto;
import com.material.website.dto.GoodsInstallDto;
import com.material.website.dto.MaterialConsumeDto;
import com.material.website.dto.MonthPlanGoodsDto;
import com.material.website.dto.StaticsDepartPlanDto;
import com.material.website.dto.StatisDepartCounsumeDto;
import com.material.website.dto.StockDto;
import com.material.website.entity.MaterialConsume;
import com.material.website.feign.config.FeignConfiguration;
import com.material.website.system.Pager;

import feign.Param;
import feign.RequestLine;

/**
 * 部门中心客户端
 * 
 * @author sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface DepartmentCenterFeign {

	/**
	 * 查询月计划分页
	 * 
	 * @param queryArgs
	 * @return
	 */
	@RequestLine("GET /queryPlanPager")
	public Pager<DeparPlanDto> queryPlanPager(@RequestParam Map<String, Object>map);

	/**
	 * 根据月计划编号查询申请商品信息
	 * 
	 * @return
	 */
	@RequestLine("GET /queryMonthPlanGoods/{planId}")
	public List<MonthPlanGoodsDto> queryMonthPlanGoods(@Param("planId") Integer planId);
	
    /**
     * 添加月计划
     * @return
     */
	@RequestLine("GET /addMonthPlan")
	public boolean addMonthPlan(@RequestParam Map<String, Object>map);
	
	/**
	 * 部门出库
	 * @param addArgs
	 * @return
	 */
	@RequestLine("GET /addDepartOutStock")
	public Map<String, Object> addDepartOutStock(@RequestParam Map<String, Object>map);
	
	/**
	 * 修改月计划状态
	 * @param planId
	 */
	 @RequestLine("GET /updateMonthPlanStatus/{planId}")
	public boolean updateMonthPlanStatus(@Param("planId") Integer planId);
	
	/***
	 * 删除月计划选购的商品
	 * @param goodsId
	 * @return
	 */
	@RequestLine("GET /delPlanGoods/{goodsId}")
	public boolean delPlanGoods(@Param("goodsId") Integer goodsId);
	
	/**
	 * 根据计划编号更新商品信息
	 * @param planId
	 * @param goodsStr
	 * @return
	 */
	@RequestLine("GET /updatePlanGoods")
	public String updatePlanGoods(@Param("planId") Integer planId,@Param("goodsStr") String goodsStr);
	
	
	 /**
     * 部门出库查询分页
     * @param queryArgs
     * @return
     */
	@RequestLine("GET /queryConsumePager")
    public Pager<MaterialConsumeDto> queryConsumePager(@RequestParam Map<String, Object>map);
    
    /**
     * 查询部门库存分页
     * @param queryArgs
     * @return
     */
	@RequestLine("GET /queryDepartStockPager")
    public Pager<DepartStockDto> queryDepartStockPager(@RequestParam Map<String, Object>map);
    
    /**
     * 根据部门查询当天最大出库量
     * @param departId
     * @return
     */
	@RequestLine("GET /queryMaxCkCout")
    public Integer queryMaxCkCout(@Param("departId") Integer departId);
    
    /**
     * 查询部门库存商品列表
     * @param queryArgs
     * @return
     */
	@RequestLine("GET /queryDepartStockList")
    public List<StockDto> queryDepartStockList(@RequestParam Map<String, Object>map);
    
    /**
     * 根据出库编号查询出库商品列表
     * @param ckNo
     * @return
     */
	@RequestLine("GET /queryCkGoodsList")
    public List<GoodsInstallDto> queryCkGoodsList(@Param("ckNo") Integer ckNo);
    
    /**
     * 根据编号更改固定资产使用状态
     * @param id
     * @param type
     * @param remarks
     */
	@RequestLine("GET /updateFixedGoodsStatus")
    public boolean updateFixedGoodsStatus(@Param("id") Integer id,@Param("type") Integer type,@Param("remarks") String remarks,@Param("departId") Integer departId,@Param("targetDepart") Integer targetDepart);
    
    /**
     * 统计部门月计划
     * @param queryArgs
     * @return
     */
	@RequestLine("GET /staticsDepartPlan")
    public Pager<StaticsDepartPlanDto> staticsDepartPlan(@RequestParam Map<String, Object>map);
	
    /**
     * 统计部门出库
     * @param queryArgs
     * @return
     */
	@RequestLine("GET /statisDepartConsumePager")
    public Pager<StatisDepartCounsumeDto> statisDepartConsumePager(@RequestParam Map<String, Object>map);
    
    /**
     * 出库更新初始化
     * @param consumeId
     * @return
     */
	@RequestLine("GET /updateDepartConsumeInit/{consumeId}")
	public Map<String, Object> updateDepartConsumeInit(@Param("consumeId") Integer consumeId);
	
	
	/**
	 * 更新部门出库信息
	 * @param updateArgs
	 * @return
	 */
	@RequestLine("GET /updateDepartConsume")
	public boolean updateDepartConsume(@RequestParam Map<String, Object>map);
	
	/**
	 * 根据物资编号查询出库信息
	 * @param operatNo
	 * @return
	 */
	@RequestLine("GET /queryConsumeInfo")
	public MaterialConsume queryConsumeInfo(@Param("operatNo") String operatNo,@Param("consumeId") Integer consumeId);
	
	/**
	 * 入账 入库信息
	 * @return
	 */
	@RequestLine("GET /addLockConsume/{consumeId}")
	public boolean addLockConsume(@Param("consumeId") Integer consumeId);
}
