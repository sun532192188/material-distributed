package com.material.website.api;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.UserDto;
import com.material.website.entity.Admin;
import com.material.website.entity.LoginLog;
import com.material.website.system.Pager;

/**
 * 用户申明式客户端
 * @author Sunxiaorong
 *
 */
@RequestMapping(value="/AdminAPI")
public interface AdminAPI {
   
	/**
	 * 根据管理员名称查询管理员信息
	 * @RequestParam username
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public Admin login(@RequestParam("username") String username,@RequestParam("password") String password);
	
	/**
	 * 加载管理员列表(分页)
	 * @return
	 *//*
	@RequestMapping(value="/queryUserPager",method=RequestMethod.GET)
	public Pager<UserDto> queryUserPager(@RequestParam("userName") String userName,@RequestParam("roleId") Integer roleId,@RequestParam("remove") Integer remove);*/
	
	/**
	 * 管理员添加
	 * @RequestParam adminArgs
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public void add(@RequestParam Map<String, Object>map);
	
	/**
	 * 修改管理员信息
	 * @RequestParam adminArgs
	 */
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public Admin update(@RequestParam Map<String, Object>map);
	
	/**
	 * 更改管理员状态
	 * @RequestParam id
	 */
	@RequestMapping(value="/updateStatus",method=RequestMethod.GET)
	public void updateStatus(@RequestParam("id") Integer id);
	
	/**
	 * 根据编号加载管理员信息
	 * @RequestParam id
	 * @return
	 */
	@RequestMapping(value="/loadAdminInfo",method=RequestMethod.GET)
	public Admin loadAdminInfo(@RequestParam("id") Integer id);
	
	/**
	 * 根据用户名查询管理员信息(检测用户名唯一性)
	 * @RequestParam username
	 * @return
	 */
	@RequestMapping(value="/loadAdminByName",method=RequestMethod.GET)
	public Admin loadAdminByName(@RequestParam("username") String username);
	
	/**
	 * 添加用户 登录日志
	 * @RequestParam userLoginLog
	 */
	@RequestMapping(value="/addUserLoginLog",method=RequestMethod.GET)
	public void addUserLoginLog(@RequestParam Map<String, Object>map);
	
	/**
	 * 根据用户名查询用户 登录日志
	 * @RequestParam userName
	 * @return
	 */
	@RequestMapping(value="/queryLogByUserName",method=RequestMethod.GET)
	public LoginLog queryLogByUserName(@RequestParam("userName") String userName);
	
	/**
	 * 根据用户名删除用户登录 日志
	 * @RequestParam userName
	 */
	@RequestMapping(value="/updateLogByUserName",method=RequestMethod.GET)
	public void updateLogByUserName(@RequestParam("userName") String userName,@RequestParam("status") Integer status);
	
	/**
	 * 删除表中的所有数据
	 */
	@RequestMapping(value="/deleteAllData",method=RequestMethod.GET)
	public boolean deleteAllData(@RequestParam("tableName") String tableName);
}
