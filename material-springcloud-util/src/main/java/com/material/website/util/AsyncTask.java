package com.material.website.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());    
     
    @Async("myTaskAsyncPool")    
    public void doTask1(int threadNum) throws InterruptedException{    
    	logger.info("Task"+threadNum+" started.");  
    	for(int i = 0;i<1000000;i++){
    		System.out.println("正在执行线程"+threadNum+" 请稍候..."+i);
    	}
    	logger.info("Task"+threadNum+" finshed.");  
    }    
}
