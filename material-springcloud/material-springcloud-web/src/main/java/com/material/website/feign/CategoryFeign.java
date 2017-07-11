package com.material.website.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.material.website.args.CategoryArgs;
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
	public Pager<CategoryDto> queryCategoryPager(String categoryName,Integer paretId,Integer status);
	/**
	 * 添加分类
	 * @param categoryArgs
	 * @return
	 */
	public boolean addCategory(CategoryArgs categoryArgs);
	/**
	 * 修改分类信息
	 * @param categoryArgs
	 * @return
	 */
	public boolean updateCategory(CategoryArgs categoryArgs);
	/**
	 * 删除分类
	 * @param categoryId
	 * @return
	 */
	public Map<String, Object>  delCategory(Integer categoryId);
	/**
	 * 根据编号加载分类信息
	 * @param categoryId
	 * @return
	 */
	public Category loadCategory(Integer categoryId);
}
