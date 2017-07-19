package com.material.website.api;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.FunctionDto;
import com.material.website.dto.RoleFunctionDto;
import com.material.website.entity.Function;

/**
 * 角色功能业务接口
 * @author sunxiaorong
 *
 */
@RequestMapping("/RoleFunctionAPI")
public interface RoleFunctionAPI {
	
	/**
	 * 根据角色编号查询
	 * @RequestParam roleId
	 * @return
	 */
	@RequestMapping(value="/queryFunctionByRoleId",method=RequestMethod.GET)
	public List<RoleFunctionDto> queryFunctionByRoleId(@RequestParam("roleId") Integer roleId);
	
	/**
	 * 根据父功能编号查询功能列表
	 * @RequestParam parentId
	 * @return
	 */
	@RequestMapping(value="/queryFunctionListByParentId",method=RequestMethod.GET)
	public List<FunctionDto> queryFunctionListByParentId(@RequestParam("parentId") Integer parentId);
	
	/**
	 * 查询 所有 的一级功能
	 * @return
	 */
	@RequestMapping(value="/queryAllFunction",method=RequestMethod.GET)
	public List<FunctionDto> queryAllFunction();
	
	/**
	 * 删除角色功能
	 * @RequestParam id
	 * @return
	 */
	@RequestMapping(value="/queryAllFunction",method=RequestMethod.GET)
	public boolean delRoleFunction(@RequestParam("id") Integer id);
	
	/**
	 * 根据id查询功能信息
	 * @RequestParam id
	 * @return
	 */
	@RequestMapping(value="/queryFunctionById",method=RequestMethod.GET)
    public Function queryFunctionById(@RequestParam("id") Integer id);
}
