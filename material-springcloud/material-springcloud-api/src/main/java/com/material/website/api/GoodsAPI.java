package com.material.website.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.GoodsDto;
import com.material.website.dto.GoodsInstallDto;
import com.material.website.entity.Goods;
import com.material.website.entity.OperatTemp;
import com.material.website.system.Pager;


/**
 * 商品客户端申明
 * @author sunxiaorong
 *
 */
@RequestMapping("/GoodsAPI")
public interface GoodsAPI {

	/**
	 * 查询商品信息(分页)
	 * @param queryArgs
	 * @return
	 */
	@RequestMapping(value="/queryGoodsPager",method=RequestMethod.GET)
	public Pager<GoodsDto> queryGoodsPager(@RequestParam Map<String, Object>map);
	
	/**
	 * 添加商品
	 * @param goodsAddArgs
	 * @return
	 */
	@RequestMapping(value="/addGoods",method=RequestMethod.GET)
	public boolean addGoods(@RequestParam Map<String, Object>map);
	
	
	/**
	 * 修改商品信息
	 * @param goodsArgs
	 * @return
	 */
	@RequestMapping(value="/updateGoods",method=RequestMethod.GET)
	public boolean updateGoods(@RequestParam Map<String, Object>map);
	
	/**
	 * 根据id加载商品
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value="/loadGoods",method=RequestMethod.GET)
	public Goods loadGoods(@RequestParam("goodsId") Integer goodsId);
	
	/**
	 * 删除商品信息
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value="/delGoods",method=RequestMethod.GET)
	public boolean delGoods(@RequestParam("goodsId") Integer goodsId);
	
	/**
	 * 查询所有商品
	 * @return
	 */
	@RequestMapping(value="/queryAllGoods",method=RequestMethod.GET)
	public List<GoodsDto> queryAllGoods(@RequestParam Map<String, Object>map);
	
	/**
	 * 查询所有的临时商品信息
	 * @param supplierId
	 * @param operatNo
	 * @return
	 */
	@RequestMapping(value="/queryAllTemp",method=RequestMethod.GET)
	public List<GoodsInstallDto> queryAllTemp(@RequestParam("supplierId") Integer supplierId,@RequestParam("operatNo") String operatNo);
	
	/**
	 * 添加操作临时表
	 * @param operatTemp
	 * @return
	 */
	@RequestMapping(value="/addOperatTemp",method=RequestMethod.GET)
	public boolean addOperatTemp(@RequestParam Map<String, Object>map);
	/**
	 * 删除临时操作表
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delOperaTemp",method=RequestMethod.GET)
	public boolean delOperaTemp(@RequestParam("id") Integer id);
	
	/**
	 * 删除所有的临时数据
	 * @param suplierId
	 * @param operatNo
	 */
	@RequestMapping(value="/delAllTemp",method=RequestMethod.GET)
	public void delAllTemp(@RequestParam("suplierId") Integer suplierId, @RequestParam("operatNo") String operatNo);
	
	/**
	 * 根据id更新临时表数据
	 * @param tempId
	 */
	@RequestMapping(value="/updateTempGoodsNum",method=RequestMethod.GET)
    public void updateTempGoodsNum(@RequestParam Map<String, Object>map);
    
    /**
     * 根据条件加载单条数据
     * @param goodsId
     * @param supplierId
     * @param operatNo
     * @return
     */
	@RequestMapping(value="/loadTemp",method=RequestMethod.GET)
    public OperatTemp loadTemp(@RequestParam("goodsId") Integer goodsId,@RequestParam("supplierId") Integer supplierId,@RequestParam("operatNo") String operatNo,@RequestParam("goodsPrice") Double goodsPrice);

   /**
    * 根据分类编号查询分类信息
    * @param categoryId
    * @return
    */
	@RequestMapping(value="/queryGoodsByCategoryId",method=RequestMethod.GET)
    public List<Goods> queryGoodsByCategoryId(@RequestParam("categoryId") Integer categoryId);
    
    /**
     * 定时删除临时表数据(防止对库存量造成影响)
     */
	@RequestMapping(value="/delTempData",method=RequestMethod.GET)
	public boolean delTempData();
}
