/*package com.material.website.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.material.website.util.AsyncTask;


@RestController
public class ThreadPoolController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired  
	private AsyncTask asyncTask;  
	
	@GetMapping("/thread/{threadnum}")
	public String findById(@PathVariable Integer threadnum) throws InterruptedException{
		for(int i = 0;i<threadnum;i++){
			asyncTask.doTask1(i);
		}
		return "线程执行成功";
	}
	
}
*/