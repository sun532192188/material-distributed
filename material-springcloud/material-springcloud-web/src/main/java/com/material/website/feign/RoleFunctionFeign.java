package com.material.website.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.material.website.dto.FunctionDto;
import com.material.website.dto.RoleFunctionDto;
import com.material.website.entity.Function;
import com.material.website.feign.config.FeignConfiguration;

import feign.Param;
import feign.RequestLine;

/**
 * 角色功能业务接口
 * @author sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface RoleFunctionFeign {
	
	/**
	 * 根据角色编号查询
	 * @param roleId
	 * @return
	 */
	@RequestLine("GET /queryFunctionByRoleId/{id}")
	public List<RoleFunctionDto> queryFunctionByRoleId(@Param("roleId") Integer roleId);
	
	/**
	 * 根据父功能编号查询功能列表
	 * @param parentId
	 * @return
	 */
	@RequestLine("GET /queryFunctionListByParentId/{parentId}")
	public List<FunctionDto> queryFunctionListByParentId(@Param("parentId") Integer parentId);
	
	/**
	 * 查询 所有 的一级功能
	 * @return
	 */
	@RequestLine("GET /queryAllFunction")
	public List<FunctionDto> queryAllFunction();
	
	/**
	 * 删除角色功能
	 * @param id
	 * @return
	 */
	@RequestLine("GET /queryAllFunction/{id}")
	public boolean delRoleFunction(@Param("id") Integer id);
	
	/**
	 * 根据id查询功能信息
	 * @param id
	 * @return
	 */
	@RequestLine("GET /queryFunctionById/{id}")
    public Function queryFunctionById(@Param("id") Integer id);
}
