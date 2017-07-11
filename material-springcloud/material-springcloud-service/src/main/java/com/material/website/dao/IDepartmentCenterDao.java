package com.material.website.dao;

import java.util.List;

import com.material.website.args.DepartPlanQueryArgs;
import com.material.website.args.DepartStockQueryArgs;
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
import com.material.website.entity.DepartStock;
import com.material.website.entity.MaterialApply;
import com.material.website.entity.MaterialConsume;
import com.material.website.system.Pager;

/**
 * 部门中心数据持久层接口
 * @author sunxiaorong
 *
 */
public interface IDepartmentCenterDao extends IBase<Object> {
	
	/**
	 * 查询月计划分页
	 * @param queryArgs
	 * @return
	 */
	public Pager<DeparPlanDto> queryPlanPager(DepartPlanQueryArgs queryArgs);

	
	/**
	 * 根据月计划编号查询申请商品信息
	 * @return
	 */
	public List<MonthPlanGoodsDto>  queryMonthPlanGoods(Integer planId);
	
	/**
	 * 根据条件查询部门库存信息
	 * @return
	 */
	public DepartStock queryDepartStockList(Integer departId,Integer goodsId,Double price,Integer stockType);
	
	/**
	 * 根据编号更改月计划申请状态
	 * @param planId
	 */
    public void updateMonthPlanStatus(Integer planId);
    
    /**
     * 根据参数查询计划商品信息
     * @param goodsId
     * @param goodsPrice
     * @return
     */
    public MaterialApply queryGoodsByParam(Integer goodsId,Double goodsPrice,Integer planId);
    
    /**
     * 部门出库查询分页
     * @param queryArgs
     * @return
     */
    public Pager<MaterialConsumeDto> queryConsumePager(MaterialConsumeQueryArgs queryArgs);
    
     /**
     * 查询部门库存分页
     * @param queryArgs
     * @return
     */
    public Pager<DepartStockDto> queryDepartStockPager(DepartStockQueryArgs queryArgs);
    
    /**
     * 根据部门查询当天最大出库量
     * @param departId
     * @return
     */
    public Integer queryMaxCkCout(Integer departId);
    
    /**
     * 查询部门库存商品列表
     * @param queryArgs
     * @return
     */
    public List<StockDto> queryDepartStockList(DepartStockQueryArgs queryArgs);
    
    /**
     * 根据出库编号查询出库商品列表
     * @param ckNo
     * @return
     */
    public List<GoodsInstallDto> queryCkGoodsList(Integer ckNo);
    
    /**
     * 根据编号更改固定资产使用状态
     * @param id
     * @param type
     * @param remarks
     */
    public void updateFixedGoodsStatus(Integer id,Integer type,String remarks);
    
    /**
     * 统计部门月计划
     * @param queryArgs
     * @return
     */
    public Pager<StaticsDepartPlanDto> staticsDepartPlan(StaticsDepartPlanArgs queryArgs);
    
    /**
     * 统计部门出库
     * @param queryArgs
     * @return
     */
    public Pager<StatisDepartCounsumeDto> statisDepartConsumePager(StatisDepartConsumeArgs queryArgs);
 
    /**
     * 根据编号查询部门库存
     * @param id
     * @return
     */
   public DepartStock queryDepartStockById(Integer id);
   
   /**
    * 根据编号查询出库信息
    * @param operatNo
    * @return
    */
   public MaterialConsume queryConsumeInfo(String operatNo,Integer consumeId);
   
}
