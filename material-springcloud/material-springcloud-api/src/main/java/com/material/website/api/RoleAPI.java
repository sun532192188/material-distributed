package com.material.website.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.entity.Role;
import com.material.website.entity.RoleFunction;


@RequestMapping("/RoleAPI")
public interface RoleAPI {

	/**
     * 角色添加
     * @RequestParam role
     * @return
     */
	@RequestMapping(value="/addRole",method=RequestMethod.GET)
	public Integer addRole(@RequestParam Map<String, Object>map);
	
	/**
	 * 查询所有的角色信息
	 * @return
	 */
	@RequestMapping(value="/queryRole",method=RequestMethod.GET)
	public List<Role> queryRole();
	
	/**
	 * 根据名称查询角色
	 * @RequestParam roleName
	 * @return
	 */
	@RequestMapping(value="/queryRoleByName",method=RequestMethod.GET)
	public Role queryRoleByName(@RequestParam("roleName") String roleName);
	
	/**
	 * 根据角色名称查询角色信息
	 * @RequestParam roleId
	 * @return
	 */
	@RequestMapping(value="/queryRoleById",method=RequestMethod.GET)
	public Role queryRoleById(@RequestParam("roleId") Integer roleId);
	
	/**
	 * 根据角色编号查询角色功能信息
	 * @RequestParam roleId
	 * @return
	 */
	@RequestMapping(value="/queryRoleFunctionByRoleId",method=RequestMethod.GET)
	public List<RoleFunction> queryRoleFunctionByRoleId(@RequestParam("roleId")Integer roleId);
	
	/**
	 * 修改角色信息
	 * @RequestParam updateArgs
	 * @return
	 */
	@RequestMapping(value="/updateRole",method=RequestMethod.GET)
	public Integer updateRole(@RequestParam Map<String, Object>map);
}
