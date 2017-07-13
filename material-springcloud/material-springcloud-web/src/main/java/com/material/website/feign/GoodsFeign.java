package com.material.website.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.material.website.args.GoodsAddArgs;
import com.material.website.args.GoodsQueryArgs;
import com.material.website.dto.GoodsDto;
import com.material.website.dto.GoodsInstallDto;
import com.material.website.entity.Goods;
import com.material.website.entity.OperatTemp;
import com.material.website.feign.config.FeignConfiguration;
import com.material.website.system.Pager;

import feign.Param;
import feign.RequestLine;

/**
 * 商品客户端申明
 * @author sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface GoodsFeign {

	/**
	 * 查询商品信息(分页)
	 * @param queryArgs
	 * @return
	 */
	@RequestMapping(value="/queryGoodsPager",method=RequestMethod.POST)
	public Pager<GoodsDto> queryGoodsPager(@RequestBody GoodsQueryArgs queryArgs);
	
	/**
	 * 添加商品
	 * @param goodsAddArgs
	 * @return
	 */
	@RequestMapping(value="/addGoods",method=RequestMethod.POST)
	public boolean addGoods(@RequestBody GoodsAddArgs goodsAddArgs);
	
	
	/**
	 * 修改商品信息
	 * @param goodsArgs
	 * @return
	 */
	@RequestMapping(value="/updateGoods",method=RequestMethod.POST)
	public boolean updateGoods(@RequestBody GoodsAddArgs goodsArgs);
	
	/**
	 * 根据id加载商品
	 * @param goodsId
	 * @return
	 */
	@RequestLine("GET /loadGoods/{goodsId}")
	public Goods loadGoods(@Param("goodsId") Integer goodsId);
	
	/**
	 * 删除商品信息
	 * @param goodsId
	 * @return
	 */
	@RequestLine("GET /delGoods/{goodsId}")
	public boolean delGoods(@Param("goodsId") Integer goodsId);
	
	/**
	 * 查询所有商品
	 * @return
	 */
	@RequestMapping(value="/queryAllGoods",method=RequestMethod.POST)
	public List<GoodsDto> queryAllGoods(@RequestBody GoodsQueryArgs queryArgs);
	
	/**
	 * 查询所有的临时商品信息
	 * @param supplierId
	 * @param operatNo
	 * @return
	 */
	@RequestLine("GET /queryAllTemp")
	public List<GoodsInstallDto> queryAllTemp(@Param("supplierId") Integer supplierId,@Param("operatNo") String operatNo);
	
	/**
	 * 添加操作临时表
	 * @param operatTemp
	 * @return
	 */
	@RequestMapping(value="/addOperatTemp",method=RequestMethod.POST)
	public boolean addOperatTemp(@RequestBody OperatTemp operatTemp);
	/**
	 * 删除临时操作表
	 * @param id
	 * @return
	 */
	@RequestLine("GET /delOperaTemp/{id}")
	public boolean delOperaTemp(@Param("id") Integer id);
	
	/**
	 * 删除所有的临时数据
	 * @param suplierId
	 * @param operatNo
	 */
	@RequestLine("GET /delAllTemp")
	public void delAllTemp(@Param("suplierId") Integer suplierId, @Param("operatNo") String operatNo);
	
	/**
	 * 根据id更新临时表数据
	 * @param tempId
	 */
	@RequestMapping(value="/updateTempGoodsNum",method=RequestMethod.POST)
    public void updateTempGoodsNum(@RequestBody OperatTemp temp);
    
    /**
     * 根据条件加载单条数据
     * @param goodsId
     * @param supplierId
     * @param operatNo
     * @return
     */
	@RequestLine("GET /loadTemp")
    public OperatTemp loadTemp(@Param("goodsId") Integer goodsId,@Param("supplierId") Integer supplierId,@Param("operatNo") String operatNo,@Param("goodsPrice") Double goodsPrice);

   /**
    * 根据分类编号查询分类信息
    * @param categoryId
    * @return
    */
	@RequestLine("GET /queryGoodsByCategoryId/{categoryId}")
    public List<Goods> queryGoodsByCategoryId(@Param("categoryId") Integer categoryId);
    
    /**
     * 定时删除临时表数据(防止对库存量造成影响)
     */
	@RequestLine("GET /delTempData")
	public boolean delTempData();
}
