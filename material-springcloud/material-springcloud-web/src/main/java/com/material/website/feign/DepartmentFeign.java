package com.material.website.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.material.website.dto.DepartmentDto;
import com.material.website.entity.Department;
import com.material.website.feign.config.FeignConfiguration;
import com.material.website.system.Pager;

import feign.Param;
import feign.RequestLine;

/**
 * 部门客户端申明
 * @author Sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface DepartmentFeign {

	/**
	 * 查询部门数据列表
	 * @return
	 */
	@RequestLine("GET /queryDepartmentList")
	public Pager<DepartmentDto> queryDepartmentList(@Param("departName") String departName,@Param("phone") String phone);
	
	/**
	 * 添加部门数据
	 * @param department
	 * @return
	 */
	@RequestMapping(value="/addDepartment",method=RequestMethod.POST)
	public  boolean addDepartment(Department department);
	
	/**
	 * 修改部门数据
	 * @param department
	 * @return
	 */
	@RequestMapping(value="/updateDepartment",method=RequestMethod.POST)
	public boolean updateDepartment(@RequestBody Department department);
	
	/**
	 * 根据编号加载部门信息
	 * @param departId
	 * @return
	 */
	@RequestLine("GET /loadDepartment/{departId}")
	public Department loadDepartment(@Param("departId") Integer departId);
	
	/**
	 * 查询所有的部门
	 * @return
	 */
	@RequestLine("GET /queryAllDepartMent")
	public List<Department> queryAllDepartMent();
}
