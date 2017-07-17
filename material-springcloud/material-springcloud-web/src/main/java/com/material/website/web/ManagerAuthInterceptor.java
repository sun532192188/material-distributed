/*package com.material.website.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.material.website.entity.Admin;
import com.material.website.system.Auth;
import com.material.website.system.ManagerType;

*//**
 * 1.创建自己的拦截器实现HandlerInterceptor接口
 * 2.创建自己的拦截器链，继承WebMvcConfigurerAdapter类，重写addInterceptors方法
 * 3.实例化自己的拦截器，并加入到拦截器链中。
 * @author Sunxiaorong
 *
 *//*
@Configuration  
public class ManagerAuthInterceptor extends WebMvcConfigurerAdapter implements HandlerInterceptor {

	*//**
	 * 在请求处理之前进行调用
	 * 只有返回true才会继续向下执行，返回false取消当前请求
	 *//*
	@Override
	public boolean  preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		  HttpSession session = request.getSession();
		    String ctx = request.getContextPath();
			if(session == null){  
	        	response.sendRedirect(ctx + "/");  
	        	return false;
	        }

			HandlerMethod hm = (HandlerMethod)handler;
			Admin loginManager = (Admin) session.getAttribute("loginManager");
			if(loginManager==null) {
				request.getSession().setAttribute("lastURL", request.getRequestURL().toString());
				response.sendRedirect(ctx+"/guest/login");
				return false;
			} else {
				Class<?> targetClass = hm.getBean().getClass();
//				Method targetMethod = hm.getMethod();
				Boolean isAdmin = (Boolean)session.getAttribute("admin");
				if((isAdmin == null || !isAdmin)) {
					if(!targetClass.isAnnotationPresent(Auth.class)) {
						response.sendRedirect(ctx+"/admin/index");
						return false;
					}
					Auth ahs = targetClass.getAnnotation(Auth.class);
					ManagerType[] values = ahs.value();
					boolean hasAuth = false;
					for(ManagerType mt:values) {
						if(mt.ordinal() == ManagerType.EVERYONE.ordinal() || mt.getId() == loginManager.getRoleId()) {
							hasAuth = true;
							break;
						}
					}
					if(!hasAuth) {
						response.sendRedirect(ctx+"/admin/index");
						return false;
					}
				}
			}
			return this.preHandle(request, response, handler);
	}
    
	*//**
	 * 请求处理之后进行调用
	 * 但是在视图被渲染之前
	 *//*
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object, ModelAndView view)
			throws Exception {
	}
	
	*//**
	 * 整个请求结束之后被调用
	 * 也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
	 *//*
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception)
			throws Exception {
	}
	
	
	@Override  
    public void addInterceptors(InterceptorRegistry registry) {  
        //众多的拦截器组成了一个拦截器链  
        *//** 
         * 主要方法说明： 
         * addPathPatterns 用于添加拦截规则 
         * excludePathPatterns 用户排除拦截 
         *//*  
        registry.addInterceptor(new ManagerAuthInterceptor()).addPathPatterns("/*");  
        super.addInterceptors(registry);  
    }  
}
*/