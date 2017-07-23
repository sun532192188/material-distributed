package com.material.website.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.material.website.api.DepartmentAPI;
import com.material.website.dao.IDepartmentDao;
import com.material.website.dto.DepartmentDto;
import com.material.website.entity.Department;
import com.material.website.system.Pager;
import com.material.website.util.BeanMapUtil;

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
	public Pager<DepartmentDto> queryDepartmentList(String departName,String phone) {
		return departmentDao.queryDepartmentList(departName, phone);
	}

	@Override
	public boolean addDepartment(Map<String, Object>map) {
		try {
			Department department = (Department) BeanMapUtil.convertMap(Department.class, map);
			departmentDao.addEntity(department);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateDepartment(Map<String, Object>map) {
		try {
			Department department = (Department) BeanMapUtil.convertMap(Department.class, map);
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
