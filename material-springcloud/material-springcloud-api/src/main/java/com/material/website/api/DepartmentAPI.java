package com.material.website.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.DepartmentDto;
import com.material.website.entity.Department;
import com.material.website.system.Pager;

/**
 * 部门客户端申明
 * @author Sunxiaorong
 *
 */
@RequestMapping("/DepartmentAPI")
public interface DepartmentAPI {

	/**
	 * 查询部门数据列表
	 * @return
	 */
	@RequestMapping(value="/queryDepartmentList",method=RequestMethod.GET)
	public Pager<DepartmentDto> queryDepartmentList(@RequestParam("departName") String departName,@RequestParam("phone") String phone,@RequestParam Map<String, Object>map);
	
	/**
	 * 添加部门数据
	 * @RequestParam department
	 * @return
	 */
	@RequestMapping(value="/addDepartment",method=RequestMethod.GET)
	public  boolean addDepartment(@RequestParam Map<String, Object>map);
	
	/**
	 * 修改部门数据
	 * @RequestParam department
	 * @return
	 */
	@RequestMapping(value="/updateDepartment",method=RequestMethod.GET)
	public boolean updateDepartment(@RequestParam Map<String, Object>map);
	
	/**
	 * 根据编号加载部门信息
	 * @RequestParam departId
	 * @return
	 */
	@RequestMapping(value="/loadDepartment",method=RequestMethod.GET)
	public Department loadDepartment(@RequestParam("departId") Integer departId);
	
	/**
	 * 查询所有的部门
	 * @return
	 */
	@RequestMapping(value="/queryAllDepartMent",method=RequestMethod.GET)
	public List<Department> queryAllDepartMent();
}
