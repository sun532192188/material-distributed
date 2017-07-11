package com.material.website.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.args.AdminArgs;
import com.material.website.config.FeignConfiguration;
import com.material.website.dto.UserDto;
import com.material.website.entity.Admin;
import com.material.website.entity.LoginLog;
import com.material.website.system.Pager;

import feign.Param;
import feign.RequestLine;

/**
 * 用户申明式客户端
 * @author Sunxiaorong
 *
 */
//@FeignClient(name = "ea")意为通知Feign在调用该接口方法时要向Eureka中查询名为ea的服务，从而得到服务URL。
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface AdminFeign {
   
	/**
	 * 根据管理员名称查询管理员信息
	 * @param username
	 * @return
	 */
	@RequestLine("GET /login")
	public Admin login(@RequestParam("username") String username,@RequestParam("paddword") String paddword);
	
	/**
	 * 加载管理员列表(分页)
	 * @return
	 */
	@RequestLine("GET /queryUserPager")
	public Pager<UserDto> queryUserPager(@RequestParam("userName") String userName,@RequestParam("roleId") Integer roleId,@RequestParam("remove") Integer remove);
	
	/**
	 * 管理员添加
	 * @param adminArgs
	 */
	@RequestMapping(value="/add" , method = RequestMethod.POST)
	public void add(@RequestBody AdminArgs userArgs);
	
	/**
	 * 修改管理员信息
	 * @param adminArgs
	 */
	@RequestMapping(value="/update" , method = RequestMethod.POST)
	public Admin update(@RequestBody AdminArgs userArgs);
	
	/**
	 * 更改管理员状态
	 * @param id
	 */
	@RequestLine("GET /updateStatus/{id}")
	public void updateStatus(@Param("id") Integer id);
	
	/**
	 * 根据编号加载管理员信息
	 * @param id
	 * @return
	 */
	@RequestLine("GET /updateStatus/{id}")
	public Admin loadAdminInfo(@Param("id") Integer id);
	
	/**
	 * 根据用户名查询管理员信息(检测用户名唯一性)
	 * @param username
	 * @return
	 */
	@RequestLine("GET /loadAdminByName/{username}")
	public Admin loadAdminByName(@Param("username") String username);
	
	/**
	 * 添加用户 登录日志
	 * @param userLoginLog
	 */
	@RequestMapping(value="/addUserLoginLog", method=RequestMethod.POST)
	public void addUserLoginLog(@RequestBody LoginLog userLoginLog);
	
	/**
	 * 根据用户名查询用户 登录日志
	 * @param userName
	 * @return
	 */
	@RequestLine("GET /queryLogByUserName/{userName}")
	public LoginLog queryLogByUserName(@Param("userName") String userName);
	
	/**
	 * 根据用户名删除用户登录 日志
	 * @param userName
	 */
	@RequestLine("GET /updateLogByUserName")
	public void updateLogByUserName(@RequestParam String userName,@RequestParam Integer status);
	
	/**
	 * 删除表中的所有数据
	 */
	@RequestLine("GET /deleteAllData/{tableName}")
	public boolean deleteAllData(@Param("tableName") String tableName);
}
