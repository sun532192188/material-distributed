package com.material.website.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.material.website.system.Pager;

/**
 * 部门中心客户端
 * 
 * @author sunxiaorong
 *
 */
@RequestMapping("/DepartmentCenterAPI")
public interface DepartmentCenterAPI {

	/**
	 * 查询月计划分页
	 * 
	 * @param queryArgs
	 * @return
	 */
	@RequestMapping(value="/queryPlanPager",method=RequestMethod.GET)
	public Pager<DeparPlanDto> queryPlanPager(@RequestParam Map<String, Object>map);

	/**
	 * 根据月计划编号查询申请商品信息
	 * 
	 * @return
	 */
	@RequestMapping(value="/queryMonthPlanGoods",method=RequestMethod.GET)
	public List<MonthPlanGoodsDto> queryMonthPlanGoods(@RequestParam("planId") String planId);
	
    /**
     * 添加月计划
     * @return
     */
	@RequestMapping(value="/addMonthPlan",method=RequestMethod.GET)
	public boolean addMonthPlan(@RequestParam Map<String, Object>map);
	
	/**
	 * 部门出库
	 *  addArgs
	 * @return
	 */
	@RequestMapping(value="/addDepartOutStock",method=RequestMethod.GET)
	public Map<String, Object> addDepartOutStock(@RequestParam Map<String, Object>map);
	
	/**
	 * 修改月计划状态
	 * @param planId
	 */
	 @RequestMapping(value="/updateMonthPlanStatus",method=RequestMethod.GET)
	public boolean updateMonthPlanStatus(@RequestParam("planId") Integer planId);
	
	/***
	 * 删除月计划选购的商品
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value="/delPlanGoods",method=RequestMethod.GET)
	public boolean delPlanGoods(@RequestParam("goodsId") Integer goodsId);
	
	/**
	 * 根据计划编号更新商品信息
	 * @param planId
	 * @param goodsStr
	 * @return
	 */
	@RequestMapping(value="/updatePlanGoods",method=RequestMethod.GET)
	public String updatePlanGoods(@RequestParam("planId") Integer planId,@RequestParam("goodsStr") String goodsStr);
	
	
	 /**
     * 部门出库查询分页
     * @param queryArgs
     * @return
     */
	@RequestMapping(value="/queryConsumePager",method=RequestMethod.GET)
    public Pager<MaterialConsumeDto> queryConsumePager(@RequestParam Map<String, Object>map);
    
    /**
     * 查询部门库存分页
     * @param queryArgs
     * @return
     */
	@RequestMapping(value="/queryDepartStockPager",method=RequestMethod.GET)
    public Pager<DepartStockDto> queryDepartStockPager(@RequestParam Map<String, Object>map);
    
    /**
     * 根据部门查询当天最大出库量
     * @param departId
     * @return
     */
	@RequestMapping(value="/queryMaxCkCout",method=RequestMethod.GET)
    public Integer queryMaxCkCout(@RequestParam("departId") Integer departId);
    
    /**
     * 查询部门库存商品列表
     * @param queryArgs
     * @return
     */
	@RequestMapping(value="/queryDepartStockList",method=RequestMethod.GET)
    public List<StockDto> queryDepartStockList(@RequestParam Map<String, Object>map);
    
    /**
     * 根据出库编号查询出库商品列表
     * @param ckNo
     * @return
     */
	@RequestMapping(value="/queryCkGoodsList",method=RequestMethod.GET)
    public List<GoodsInstallDto> queryCkGoodsList(@RequestParam("ckNo") Integer ckNo);
    
    /**
     * 根据编号更改固定资产使用状态
     * @param id
     * @param type
     * @param remarks
     */
	@RequestMapping(value="/updateFixedGoodsStatus",method=RequestMethod.GET)
    public boolean updateFixedGoodsStatus(@RequestParam("id") Integer id,@RequestParam("type") Integer type, @RequestParam("remarks") String remarks,@RequestParam("departId") Integer departId,@RequestParam("targetDepart") Integer targetDepart);
    
    /**
     * 统计部门月计划
     * @param queryArgs
     * @return
     */
	@RequestMapping(value="/staticsDepartPlan",method=RequestMethod.GET)
    public Pager<StaticsDepartPlanDto> staticsDepartPlan(@RequestParam Map<String, Object>map);
	
    /**
     * 统计部门出库
     * @param queryArgs
     * @return
     */
	@RequestMapping(value="/statisDepartConsumePager",method=RequestMethod.GET)
    public Pager<StatisDepartCounsumeDto> statisDepartConsumePager(@RequestParam Map<String, Object>map);
    
    /**
     * 出库更新初始化
     * @param consumeId
     * @return
     */
	@RequestMapping(value="/updateDepartConsumeInit",method=RequestMethod.GET)
	public Map<String, Object> updateDepartConsumeInit(@RequestParam("consumeId") Integer consumeId);
	
	
	/**
	 * 更新部门出库信息
	 * @param updateArgs
	 * @return
	 */
	@RequestMapping(value="/updateDepartConsume",method=RequestMethod.GET)
	public boolean updateDepartConsume(@RequestParam Map<String, Object>map);
	
	/**
	 * 根据物资编号查询出库信息
	 * @param operatNo
	 * @return
	 */
	@RequestMapping(value="/queryConsumeInfo",method=RequestMethod.GET)
	public MaterialConsume queryConsumeInfo(@RequestParam("operatNo") String operatNo,@RequestParam("consumeId") Integer consumeId);
	
	/**
	 * 入账 入库信息
	 * @return
	 */
	@RequestMapping(value="/addLockConsume",method=RequestMethod.GET)
	public boolean addLockConsume(@RequestParam("consumeId") Integer consumeId);
}
