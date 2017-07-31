package com.material.website.systemcontext;

import java.util.Map;

import com.material.website.system.SystemContext;

public class ConverMapToSystemContext {
	
    public static void convertSystemContext(Map<String, Object>map){
    	for(String key:map.keySet()){
    		switch (key) {
    		case "pageSize":
    			SystemContext.setPageSize(Integer.valueOf(map.get(key).toString()));
    			break;
    		case "currentPage":
    			SystemContext.setCurrentPage(Integer.valueOf(map.get(key).toString()));
    			break;
    		case "pageOffset":
    			SystemContext.setPageOffset(Integer.valueOf(map.get(key).toString()));
    			break;
    		case "sort":
    			SystemContext.setSort(map.get(key).toString());
    			break;
    		case "order":
    			SystemContext.setOrder(map.get(key).toString());
    			break;
    		default:
    			break;
    		}
    	}
    }
}
