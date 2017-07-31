package com.material.website.web.interceptor;

import java.util.LinkedHashMap;
import java.util.Map;

import com.material.website.system.SystemContext;

public class GetSystemContext {
    public static Map<String, Object> map; 
	
	public static Map<String, Object> getSystemMap(){
		map =  new LinkedHashMap<String,Object>();
		map.put("pageSize", SystemContext.getPageSize());
		map.put("currentPage", SystemContext.getCurrentPage());
		map.put("pageOffset", SystemContext.getPageOffset());
		map.put("sort", SystemContext.getSort());
		map.put("order", SystemContext.getOrder());
		return map;
	}
}
