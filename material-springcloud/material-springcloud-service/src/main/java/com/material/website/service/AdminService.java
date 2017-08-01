package com.material.website.service;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.material.website.api.AdminAPI;
import com.material.website.args.AdminArgs;
import com.material.website.dao.IAdminDao;
import com.material.website.dto.UserDto;
import com.material.website.entity.Admin;
import com.material.website.entity.LoginLog;
import com.material.website.system.Pager;
import com.material.website.systemcontext.ConverMapToSystemContext;
import com.material.website.util.JsonUtil;
import com.material.website.util.SecurityUtil;


/**  
 * @Description: 管理员业务实现类(功能描述) 
 * @author 孙晓荣 sunxiaorong@yuntengzhiyong.com  
 * @date 2015年5月20日 下午2:23:40  
 */

	
@RestController
@Transactional 
public class AdminService implements AdminAPI{

	@Autowired
	private IAdminDao adminDao;

	/* (non-Javadoc)
	 * @see org.ytzy.app.service.IAdminService#loadAdminByName(java.lang.String)
	 */
	@Override
	public Admin login(String username,String password){
		 Admin admin=adminDao.loadAdminByName(username);
		if(admin==null||admin.getRemove()==1||!admin.getPassword().equals(SecurityUtil.password(password))){
			throw new RuntimeException("用户名或密码错误");
		}
		return admin;
	}

	/* (non-Javadoc)
	 * @see org.ytzy.app.service.IAdminService#loadAdmin()
	 */
	@Override
	public Pager<UserDto> queryUserPager(String userName,Integer roleId,Integer remove,@RequestParam Map<String, Object>map){
		ConverMapToSystemContext.convertSystemContext(map);
		return adminDao.queryUserPager(userName, roleId,remove);
	}

	/* (non-Javadoc)
	 * @see org.ytzy.app.service.IAdminService#add(org.ytzy.app.args.AddAdminArgs)
	 */
	@Override
	public void add(String json) {
		AdminArgs adminArgs = (AdminArgs) JsonUtil.newInstance().json2obj(json, AdminArgs.class);
		Admin admin=new Admin();
		BeanUtils.copyProperties(adminArgs, admin);
		admin.setPassword(SecurityUtil.password(adminArgs.getPassword()));
		admin.setRemove(0);
		adminDao.addEntity(admin);
	}

	/* (non-Javadoc)
	 * @see org.ytzy.app.service.IAdminService#update(org.ytzy.app.args.AddAdminArgs)
	 */
	@Override
	public Admin update(String json) {
		AdminArgs adminArgs = (AdminArgs) JsonUtil.newInstance().json2obj(json, AdminArgs.class);
		Admin admin= adminDao.get(adminArgs.getId());
		if(admin != null){
			String pwd = admin.getPassword();
			BeanUtils.copyProperties(adminArgs, admin);
			if(!adminArgs.getPassword().equals(pwd)){
				admin.setPassword(SecurityUtil.password(adminArgs.getPassword()));
			}
		}
		admin.setRemove(0);
		adminDao.updateEntity(admin);
		return admin;
	}
	

	/* (non-Javadoc)
	 * @see org.ytzy.app.service.IAdminService#updateStatus(java.lang.Integer)
	 */
	@Override
	public void updateStatus(Integer id) {
       adminDao.updateStatus(id);
	}

	/* (non-Javadoc)
	 * @see org.ytzy.app.service.IAdminService#loadAdminInfo(java.lang.Integer)
	 */
	@Override
	public Admin loadAdminInfo(Integer id) {
		return  (Admin) adminDao.get(id);
	}

	/* (non-Javadoc)
	 * @see org.ytzy.app.service.IAdminService#loadAdminByName(java.lang.String)
	 */
	@Override
	public Admin loadAdminByName(String username) {
		return adminDao.loadAdminByName(username);
	}
	
	@Override
	public void addUserLoginLog(String json) {
		LoginLog userLoginLog =  (LoginLog) JsonUtil.newInstance().json2obj(json, LoginLog.class);
		 adminDao.addEntity(userLoginLog);
	}
	

	@Override
	public  LoginLog queryLogByUserName(String userName) {
		return adminDao.queryLogByUserName(userName);
	}

	@Override
	public void updateLogByUserName(String userName, Integer status) {
		adminDao.updateLogByUserName(userName, status);
	}

	@Override
	public boolean deleteAllData(String tableName) {
		try {
			adminDao.deleteAllData(tableName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
