package com.material.website.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.material.website.args.DepartPlanAddArgs;
import com.material.website.args.DepartPlanQueryArgs;
import com.material.website.args.DepartStockQueryArgs;
import com.material.website.args.MaterialConsumeAddArgs;
import com.material.website.args.MaterialConsumeQueryArgs;
import com.material.website.args.StaticsDepartPlanArgs;
import com.material.website.args.StatisDepartConsumeArgs;
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
	@RequestMapping(value="/queryPlanPager",method=RequestMethod.POST)
	public Pager<DeparPlanDto> queryPlanPager(@RequestBody DepartPlanQueryArgs queryArgs);

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
	@RequestMapping(value="/addMonthPlan",method=RequestMethod.POST)
	public boolean addMonthPlan(@RequestBody DepartPlanAddArgs addArgs);
	
	/**
	 * 部门出库
	 * @param addArgs
	 * @return
	 */
	@RequestMapping(value="/addDepartOutStock",method=RequestMethod.POST)
	public Map<String, Object> addDepartOutStock(@RequestBody MaterialConsumeAddArgs addArgs);
	
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
	@RequestMapping(value="/queryConsumePager",method=RequestMethod.POST)
    public Pager<MaterialConsumeDto> queryConsumePager(@RequestBody MaterialConsumeQueryArgs queryArgs);
    
    /**
     * 查询部门库存分页
     * @param queryArgs
     * @return
     */
	@RequestMapping(value="/queryDepartStockPager",method=RequestMethod.POST)
    public Pager<DepartStockDto> queryDepartStockPager(@RequestBody DepartStockQueryArgs queryArgs);
    
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
	@RequestMapping(value="/queryDepartStockList",method=RequestMethod.POST)
    public List<StockDto> queryDepartStockList(@RequestBody DepartStockQueryArgs queryArgs);
    
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
	@RequestMapping(value="/staticsDepartPlan",method=RequestMethod.POST)
    public Pager<StaticsDepartPlanDto> staticsDepartPlan(@RequestBody StaticsDepartPlanArgs queryArgs);
	
    /**
     * 统计部门出库
     * @param queryArgs
     * @return
     */
	@RequestMapping(value="/statisDepartConsumePager",method=RequestMethod.POST)
    public Pager<StatisDepartCounsumeDto> statisDepartConsumePager(@RequestBody StatisDepartConsumeArgs queryArgs);
    
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
	@RequestMapping(value="/updateDepartConsume",method=RequestMethod.POST)
	public boolean updateDepartConsume(@RequestBody MaterialConsumeAddArgs updateArgs);
	
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
