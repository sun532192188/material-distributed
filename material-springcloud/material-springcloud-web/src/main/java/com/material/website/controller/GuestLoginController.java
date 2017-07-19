package com.material.website.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.material.website.dto.RoleFunctionDto;
import com.material.website.entity.Admin;
import com.material.website.feign.AdminFeign;
import com.material.website.feign.RoleFunctionFeign;
import com.material.website.system.Auth;
import com.material.website.system.ManagerType;
import com.material.website.util.PropertiesUtil;

/**  
 * @Description:管理员操作控制类(功能描述) 
 * @author 孙晓荣 sunxiaorong@yuntengzhiyong.com  
 * @date 2015年5月20日 下午3:59:27  
 */
//在之前spring使用中使用该注解@Controller
@Controller
@RequestMapping(value="/guest")
@Auth(ManagerType.EVERYONE)
public class GuestLoginController {
	
	@Autowired
	private AdminFeign adminFeign;
	@Autowired
	private RoleFunctionFeign roleFunctionFeign;
	
	/**
	 * 登录
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/loginStart")
	public String login(Model model) throws UnsupportedEncodingException {
		String systemName = PropertiesUtil.newInstance().loadValue("system_name", "system_config.properties");
	   // systemName = new String(systemName.getBytes("ISo-8859-1"),"UTF-8");
		model.addAttribute("systemName",systemName);
		return "admin/login";
	}
	
	/**
	 * 登陆
	 * @return
	 */
	@GetMapping("/login")
	//@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username,String password,HttpSession session,Model model){
		/*Admin  obj= (Admin) session.getAttribute("loginManager");
		if(obj!=null){
			return  "redirect:/admin/index";
		}*/
		String systemName = PropertiesUtil.newInstance().loadValue("system_name", "system_config.properties");
		model.addAttribute("systemName",systemName);
		List<String>errors=new ArrayList<String>();
		if(StringUtils.isEmpty(username)){
			errors.add("用户名不能为空");
		}
		if(StringUtils.isEmpty(password)){
			errors.add("密码不能为空");
		}
		if(errors.size()>0){
			model.addAttribute("errors",errors);
			return "admin/login";
		}
		try {
			Admin loginManager=adminFeign.login(username, password);
		/*	LoginLog log = adminFeign.queryLogByUserName(username);
			if(log != null){
				if(log.getStatus() == 1){
					errors.add("该账号已在其他设备中登录");
					model.addAttribute("errors",errors);
					return "admin/login";
				}else{
					adminFeign.updateLogByUserName(username, 1);
				}
			}else{
				log = new LoginLog();
				log.setLoginTime(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
				log.setStatus(1);
				log.setUserId(loginManager.getId());
				log.setUserName(loginManager.getUserName());
				adminFeign.addUserLoginLog(log);
			}*/
			model.addAttribute("loginManager",loginManager);
			session.setAttribute("loginManager",loginManager);
		    List<RoleFunctionDto>roleList = roleFunctionFeign.queryFunctionByRoleId(loginManager.getRoleId());
		    model.addAttribute("roleList",roleList);
		 } catch (Exception e) {
			e.printStackTrace();
			errors.add(e.getMessage());
		}
		if(errors.size() > 0) {
			model.addAttribute("errors",errors);
			return "admin/login";
		} else {
			Object lastURL = session.getAttribute("lastURL");
			if(lastURL != null)
				return "redirect:"+lastURL;
			else 
				return "redirect:/admin/index";
		}
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
	/*	Admin loginManager= (Admin) session.getAttribute("loginManager");
		adminFeign.updateLogByUserName(loginManager.getUserName(), 0);*/
		session.invalidate();
		return "redirect:/guest/login";
	}
}
