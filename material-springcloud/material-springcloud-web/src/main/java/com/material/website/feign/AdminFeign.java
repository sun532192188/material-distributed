package com.material.website.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.UserDto;
import com.material.website.entity.Admin;
import com.material.website.entity.LoginLog;
import com.material.website.feign.config.FeignConfiguration;
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
	public Admin login(@Param("username") String username,@Param("paddword") String paddword);
	
	/**
	 * 加载管理员列表(分页)
	 * @return
	 */
	@RequestLine("GET /queryUserPager")
	public Pager<UserDto> queryUserPager(@Param("userName") String userName,@Param("roleId") Integer roleId,@Param("remove") Integer remove);
	
	/**
	 * 管理员添加
	 * @param adminArgs
	 */
	@RequestLine("GET /add")
	public void add(@RequestParam Map<String, Object>map);
	
	/**
	 * 修改管理员信息
	 * @param adminArgs
	 */
	@RequestLine("GET /update")
	public Admin update(@RequestParam Map<String, Object>map);
	
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
	@RequestLine("GET /addUserLoginLog")
	public void addUserLoginLog(@RequestParam Map<String, Object>map);
	
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
	public void updateLogByUserName(@Param("userName") String userName,@Param("status") Integer status);
	
	/**
	 * 删除表中的所有数据
	 */
	@RequestLine("GET /deleteAllData/{tableName}")
	public boolean deleteAllData(@Param("tableName") String tableName);
}
