package com.material.website.util;

import java.util.HashMap;
import java.util.Map;

public class Test {
	
    public static void main(String[] args) {
       Map<String, String>map = new HashMap<String, String>();
       map.put("532192188@qq.com", "孙晓荣");
	   MailUtil.newInstance().sendTextMail("测试", "测试测试",map);	
	}
}
