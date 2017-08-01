package com.material.website.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.material.website.api.DepartmentAPI;
import com.material.website.dao.IDepartmentDao;
import com.material.website.dto.DepartmentDto;
import com.material.website.entity.Department;
import com.material.website.system.Pager;
import com.material.website.systemcontext.ConverMapToSystemContext;
import com.material.website.util.JsonUtil;

/**
 * 部门业务实现类
 * @author sunxiaorong
 *
 */
@RestController
@Transactional 
public class DepartmentService implements DepartmentAPI {
	
	@Autowired 
	private IDepartmentDao departmentDao;
	

	@Override
	public Pager<DepartmentDto> queryDepartmentList(String departName,String phone,@RequestParam Map<String, Object>map) {
		ConverMapToSystemContext.convertSystemContext(map);
		return departmentDao.queryDepartmentList(departName, phone);
	}

	@Override
	public boolean addDepartment(String json) {
		try {
			//Department department = (Department) BeanMapUtil.convertMap(Department.class, map);
			Department department = (Department) JsonUtil.newInstance().json2obj(json, Department.class);
			System.out.println(department.getDepartmentName());
			System.out.println(department.getPhone());
			departmentDao.addEntity(department);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	@Override
	public boolean updateDepartment(String json) {
		try {
			Department department = (Department) JsonUtil.newInstance().json2obj(json, Department.class);
			departmentDao.updateEntity(department);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Department loadDepartment(Integer departId) {
		Department department = departmentDao.get(departId);
		return department;
	}

	@Override
	public List<Department> queryAllDepartMent() {
		return departmentDao.queryAllDepartMent();
	}


}
