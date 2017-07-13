package com.material.website.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.material.website.args.RoleAddArgs;
import com.material.website.entity.Role;
import com.material.website.entity.RoleFunction;
import com.material.website.feign.config.FeignConfiguration;

import feign.Param;
import feign.RequestLine;

@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface RoleFeign {

	/**
     * 角色添加
     * @param role
     * @return
     */
	@RequestMapping(value="/addRole",method=RequestMethod.POST)
	public Integer addRole(@RequestBody RoleAddArgs roleArgs);
	
	/**
	 * 查询所有的角色信息
	 * @return
	 */
	@RequestLine("GET /queryRole")
	public List<Role> queryRole();
	
	/**
	 * 根据名称查询角色
	 * @param roleName
	 * @return
	 */
	@RequestLine("GET /queryRoleByName/{roleName}")
	public Role queryRoleByName(@Param("roleName") String roleName);
	
	/**
	 * 根据角色名称查询角色信息
	 * @param roleId
	 * @return
	 */
	@RequestLine("GET /queryRoleById/{roleId}")
	public Role queryRoleById(@Param("roleId") Integer roleId);
	
	/**
	 * 根据角色编号查询角色功能信息
	 * @param roleId
	 * @return
	 */
	@RequestLine("GET /queryRoleFunctionByRoleId/{roleId}")
	public List<RoleFunction> queryRoleFunctionByRoleId(@Param("roleId")Integer roleId);
	
	/**
	 * 修改角色信息
	 * @param updateArgs
	 * @return
	 */
	@RequestMapping(value="/updateRole",method=RequestMethod.POST)
	public Integer updateRole(@RequestBody RoleAddArgs updateArgs);
}
