/*
 * Copyright (c) 2014.
 * 北京云腾致用科技有限公司
 */
package com.material.website.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**  
 * @Description: 处理数字截取
 * @author 张明虎 zhangminghu@yuntengzhiyong.com  
 * @date 2014年12月28日 上午2:21:03  
 */

public class BigDecimaUtil {
	private static NumberFormat ins = DecimalFormat.getNumberInstance();
	/**
	 * 获取指定位数的数值
	 * @param fbi   取fbi位小数
	 * @param value double值
	 * @return
	 */
	public static String formatDouble(int fbi,Double value) {
		ins.setMaximumFractionDigits(fbi);
		return ins.format(value);
	}
	
	/**
	 * 四舍五入法
	 * @param value
	 * @return
	 */
	public static Double formatDouble(Double value){
	    long   l1   =   Math.round(value*100);   //四舍五入   
        double   ret   =   l1/100.00;  //注意：使用   100.0   而不是   100   
        return   ret;   
	}
	
	/**
	 * 格式化金钱
	 * @param value
	 * @return
	 */
	public static String formatMoney(Double value){
		DecimalFormat df = new DecimalFormat("#,###.00");
		String m = df.format(value);
		if(m.startsWith(".")){
			m = 0+m;
		}
		return m;
	}
	
	
	public static void main(String[] args) {
		Double sumMoney = (double) 0.94;
		System.out.println(formatMoney(sumMoney));
		
	}
}
