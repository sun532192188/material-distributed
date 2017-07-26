package com.material.website.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.material.website.dao.BaseDao;
import com.material.website.dao.IRoleFunctionDao;
import com.material.website.dto.FunctionDto;
import com.material.website.dto.RoleFunctionDto;
import com.material.website.entity.RoleFunction;

/**
 * 角色功能持久层实现类
 * @author sunxiaorong
 *
 */
@Repository
public class RoleFunctionDao extends BaseDao<RoleFunction> implements
		IRoleFunctionDao {

	@Override
	public List<RoleFunctionDto> queryFunctionByRoleId(Integer roleId) {
		String sql = "select * from rolefunction where roleId = ? order by functionId asc ";
		//System.out.println(super.listBySql(sql, roleId,HashMap.class, false));
		List<RoleFunction> roleFunctionList =  super.listBySql(sql, roleId,RoleFunction.class, true);
		for(RoleFunction dto:roleFunctionList){
			System.out.println(dto.getId()+":"+dto.getFunctionName()+":"+dto.getFunctionId()+":"+dto.getRoleId()+":"+dto.getUrl());
		    System.out.println("-------------华丽的分隔线---------------");
		}
		return  super.listBySql(sql, roleId, RoleFunctionDto.class, false);
	}

	@Override
	public List<FunctionDto> queryFunctionListByParentId(Integer parentId) {
		String sql = "select id,functionName, url,parentId from function where parentId = ?";
		List<FunctionDto> list = super.listBySql(sql, parentId, FunctionDto.class, false);
		return list;
	}

	@Override
	public List<FunctionDto> queryAllFunction() {
		String sql = "select * from function where parentId = 0 order by id asc ";
		return super.listBySql(sql, FunctionDto.class,false);
	}
}
