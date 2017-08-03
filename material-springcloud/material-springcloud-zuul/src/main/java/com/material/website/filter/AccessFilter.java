package com.material.website.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class AccessFilter extends ZuulFilter{
	
	private static Logger log = LoggerFactory.getLogger(AccessFilter.class);
	
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info("send {} request to{}", request.getMethod () ,
		request.getRequestURL().toString());
		Object accessToken = request.getParameter("accessToken");
		if(accessToken == null) {
			log.warn("access token is empty");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			//通过ctx.setResponseBody(body)对返回的body内容进行编辑等。
			ctx.setResponseBody("请求没带token被过滤了");
			return null;
		}
		log.info("access token ok");
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return false;
	}

	@Override
	public int filterOrder() {
		return 0;
	}
}
