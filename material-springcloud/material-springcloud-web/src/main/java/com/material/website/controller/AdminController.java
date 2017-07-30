package com.material.website.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.material.website.dto.FunctionDto;
import com.material.website.dto.RoleFunctionDto;
import com.material.website.entity.Admin;
import com.material.website.entity.Function;
import com.material.website.feign.RoleFunctionFeign;
import com.material.website.system.Auth;
import com.material.website.system.ManagerType;
import com.material.website.util.PropertiesUtil;

/**
 * @Description: 进入后台管理系统、这儿的方法需要权限控制
 * @author 张明虎 zhangminghu@yuntengzhiyong.com
 * @date 2014年12月11日 下午6:04:38
 */
@Controller
@RequestMapping(value = "/admin")
@Auth(ManagerType.EVERYONE)
public class AdminController {
    
	@Autowired
    RoleFunctionFeign roleFuncitonFeign; 
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String ids(HttpServletRequest request, Model model) {
		return "admin/module";
	}

	@RequestMapping(value = "/module", method = RequestMethod.GET)
	public String ids(Integer parentId, String parentFunctionName,
			HttpServletRequest request, Model model)
			throws UnsupportedEncodingException {
	/*	parentFunctionName = new String(
				parentFunctionName.getBytes("ISO-8859-1"), "UTF-8");*/
		model.addAttribute("parentId", parentId);
		model.addAttribute("parentFunctionName", parentFunctionName);
		return "admin/index";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String top(Model model) throws UnsupportedEncodingException {
		String systemName = PropertiesUtil.newInstance().loadValue("system_name", "system_config.properties");
	  //  systemName = new String(systemName.getBytes("ISo-8859-1"),"UTF-8");
		model.addAttribute("systemName",systemName);
		return "admin/top";
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String left(Integer parentId, String parentFunctionName, Model model)
			throws UnsupportedEncodingException {
		Function function =  roleFuncitonFeign.queryFunctionById(parentId);
		if(function != null){
			model.addAttribute("parentFunctionName",function.getFunctionName());
		}
		List<FunctionDto> resultList = roleFuncitonFeign
				.queryFunctionListByParentId(parentId);
		model.addAttribute("functionList", resultList);
		return "admin/left";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String index() {
		return "admin/main";
	}

	/**
	 * 模块功能
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/moduleFunction", method = RequestMethod.GET)
	public String moduleFunction(Model model,HttpSession session) {
		Admin manager = (Admin) session.getAttribute("loginManager");
		if (manager != null) {
			Integer roleId = manager.getRoleId();
			List<RoleFunctionDto> roleFunctionList = roleFuncitonFeign
					.queryFunctionByRoleId(roleId);
			model.addAttribute("roleFunctionList", roleFunctionList);
		}
		return "admin/moduleFunction";
	}
}