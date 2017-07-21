package com.material.website.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.material.website.api.RoleAPI;
import com.material.website.args.RoleAddArgs;
import com.material.website.dao.IRoleDao;
import com.material.website.entity.Function;
import com.material.website.entity.Role;
import com.material.website.entity.RoleFunction;
import com.material.website.util.BeanMapUtil;

/**
 * 角色业务实现类
 * @author sunxiaorong
 *
 */
@RestController
@Transactional 
public class RoleService implements RoleAPI {
	
	@Autowired
	private IRoleDao roleDao;

	@Override
	public Integer addRole(Map<String, Object>map) {
		RoleAddArgs roleArgs = (RoleAddArgs) BeanMapUtil.convertMap(RoleAddArgs.class, map);
		Role role = new Role();
		role.setRoleName(roleArgs.getRoleName());
		role = roleDao.add(role);
		if(role != null){
			String[]roleFunctionArray =  roleArgs.getFunctions().split(",");
			for(String str :roleFunctionArray){
				Function function =  (Function) roleDao.getEntity(Function.class, Integer.valueOf(str));
			    RoleFunction roleFunction = new RoleFunction();
			    roleFunction.setFunctionId(function.getId());
			    roleFunction.setFunctionName(function.getFunctionName());
			    roleFunction.setRoleId(role.getId());
			    roleFunction.setUrl(function.getUrl());
			    roleDao.addEntity(roleFunction);
			}
			return role.getId();
		}
		return -1;
	}


	@Override
	public List<Role> queryRole() {
		return roleDao.queryRole();
	}
	
	@Override
	public Role queryRoleByName(String roleName){
		return roleDao.queryRoleByName(roleName);
	}


	@Override
	public Role queryRoleById(Integer roleId) {
		return roleDao.get(roleId);
	}


	@Override
	public List<RoleFunction> queryRoleFunctionByRoleId(Integer roleId) {
		return roleDao.queryRoleFunctionByRoleId(roleId);
	}


	@Override
	public Integer updateRole(Map<String, Object>map) {
		RoleAddArgs updateArgs = (RoleAddArgs) BeanMapUtil.convertMap(RoleAddArgs.class, map);
		Role role = new Role();
		role.setRoleName(updateArgs.getRoleName());
		role.setId(updateArgs.getId());
	    roleDao.update(role);
	    roleDao.delRoleFunctionByRoleId(updateArgs.getId());
	    String[]roleFunctionArray =  updateArgs.getFunctions().split(",");
		for(String str :roleFunctionArray){
			Function function =  (Function) roleDao.getEntity(Function.class, Integer.valueOf(str));
		    RoleFunction roleFunction = new RoleFunction();
		    roleFunction.setFunctionId(function.getId());
		    roleFunction.setFunctionName(function.getFunctionName());
		    roleFunction.setRoleId(role.getId());
		    roleFunction.setUrl(function.getUrl());
		    roleDao.addEntity(roleFunction);
		}
	    return updateArgs.getId();
	}
}
