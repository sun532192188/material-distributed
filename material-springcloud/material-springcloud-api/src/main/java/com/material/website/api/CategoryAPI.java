package com.material.website.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.CategoryDto;
import com.material.website.entity.Category;
import com.material.website.system.Pager;


/**
 * 分类api
 * @author sunxiaorong
 *
 */
@RequestMapping("/CategoryAPI")
public interface CategoryAPI {

	/**
	 * 查询分类列表
	 * @RequestParam parentId
	 * @return
	 */
	@RequestMapping(value="/queryCategoryList",method=RequestMethod.GET)
	public List<CategoryDto> queryCategoryList(@RequestParam("parentId") Integer parentId);
	
	/**
	 * 查询分类(分页)
	 * @RequestParam categoryName
	 * @RequestParam paretId
	 * @return
	 */
	@RequestMapping(value="/queryCategoryPager",method=RequestMethod.GET)
	public Pager<CategoryDto> queryCategoryPager(@RequestParam("categoryName") String categoryName,@RequestParam("paretId") Integer paretId,
			@RequestParam("status") Integer status,@RequestParam Map<String, Object>map);
	/**
	 * 添加分类
	 * @RequestParam categoryArgs
	 * @return
	 */
	@RequestMapping(value="/addCategory",method=RequestMethod.POST)
	public boolean addCategory(@RequestParam("json") String json);
	/**
	 * 修改分类信息
	 * @RequestParam categoryArgs
	 * @return
	 */
	@RequestMapping(value="/updateCategory",method=RequestMethod.POST)
	public boolean updateCategory(@RequestParam("json") String json);
	/**
	 * 删除分类
	 * @RequestParam categoryId
	 * @return
	 */
	@RequestMapping(value="/delCategory",method=RequestMethod.GET)
	public Map<String, Object>  delCategory(@RequestParam("categoryId") Integer categoryId);
	/**
	 * 根据编号加载分类信息
	 * @RequestParam categoryId
	 * @return
	 */
	@RequestMapping(value="/loadCategory",method=RequestMethod.GET)
	public Category loadCategory(@RequestParam("categoryId") Integer categoryId);
}
