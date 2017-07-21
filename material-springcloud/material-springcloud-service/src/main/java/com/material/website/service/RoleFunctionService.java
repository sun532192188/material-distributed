package com.material.website.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.material.website.api.RoleFunctionAPI;
import com.material.website.dao.IRoleFunctionDao;
import com.material.website.dto.FunctionDto;
import com.material.website.dto.RoleFunctionDto;
import com.material.website.entity.Function;
import com.material.website.entity.RoleFunction;
import com.material.website.util.PropertiesUtil;

/**
 * 角色功能业务实现类
 * @author sunxiaorong
 *
 */
@RestController
@Transactional 
public class RoleFunctionService implements RoleFunctionAPI {
	
	@Autowired
	private IRoleFunctionDao roleFunctionDao;

	@Override
	public List<RoleFunctionDto> queryFunctionByRoleId(Integer roleId) {
		return roleFunctionDao.queryFunctionByRoleId(roleId);
	}

	@Override
	public List<FunctionDto> queryFunctionListByParentId(Integer parentId) {
		String urlPrefix = PropertiesUtil.newInstance().loadValue("system.url", "system.properties");
		List<FunctionDto>  list = roleFunctionDao.queryFunctionListByParentId(parentId);
		List<FunctionDto>  resultList = new ArrayList<FunctionDto>();
		for(FunctionDto dto:list){
			dto.setUrl(urlPrefix+dto.getUrl());
			resultList.add(dto);
		}
		return resultList;
	}

	@Override
	public List<FunctionDto> queryAllFunction() {
		return roleFunctionDao.queryAllFunction();
	}

	@Override
	public boolean delRoleFunction(Integer id) {
		try {
			RoleFunction roleFunction = roleFunctionDao.get(id);
			roleFunctionDao.deleteEntity(roleFunction);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Function queryFunctionById(Integer id) {
		return (Function) roleFunctionDao.getEntity(Function.class, id);
	}
}
