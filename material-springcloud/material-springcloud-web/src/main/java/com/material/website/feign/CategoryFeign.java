package com.material.website.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.CategoryDto;
import com.material.website.entity.Category;
import com.material.website.feign.config.FeignConfiguration;
import com.material.website.system.Pager;

import feign.Param;
import feign.RequestLine;


@FeignClient(name="material-springcloud-service",configuration = FeignConfiguration.class)
public interface CategoryFeign {

	/**
	 * 查询分类列表
	 * @param parentId
	 * @return
	 */
	@RequestLine("GET /queryCategoryList/{parentId}")
	public List<CategoryDto> queryCategoryList(@Param("parentId") Integer parentId);
	
	/**
	 * 查询分类(分页)
	 * @param categoryName
	 * @param paretId
	 * @return
	 */
	@RequestLine("GET /queryCategoryPager")
	public Pager<CategoryDto> queryCategoryPager(@Param("categoryName") String categoryName,@Param("paretId") Integer paretId,@Param("status") Integer status);
	/**
	 * 添加分类
	 * @param categoryArgs
	 * @return
	 */
	/*@RequestMapping(value="/addCategory",method=RequestMethod.GET)*/
	@RequestLine("GET /addCategory")
	public boolean addCategory(@RequestParam  Map<String, Object>map);
	/**
	 * 修改分类信息
	 * @param categoryArgs
	 * @return
	 */
/*	@RequestMapping(value="/updateCategory",method=RequestMethod.GET)*/
	@RequestLine("GET /updateCategory")
	public boolean updateCategory(@RequestParam Map<String, Object>map);
	/**
	 * 删除分类
	 * @param categoryId
	 * @return
	 */
	@RequestLine("GET /delCategory/{categoryId}")
	public Map<String, Object>  delCategory(@Param("categoryId") Integer categoryId);
	/**
	 * 根据编号加载分类信息
	 * @param categoryId
	 * @return
	 */
	@RequestLine("GET /loadCategory/{categoryId}")
	public Category loadCategory(@Param("categoryId") Integer categoryId);
}
