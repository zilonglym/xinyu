package com.xinyu.controller.task;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;

@Lazy(value=false)
@Controller
@RequestMapping(value="system")
public class SystemTask  extends BaseController{

	@RequestMapping(value="index")
	@ResponseBody
	public Map<String,Object> index(){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("ret", "OK");
		return resultMap;
	}
	
	
	@RequestMapping(value="index1")
	@ResponseBody
	public Map<String,Object> index1(){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		System.gc();
		resultMap.put("ret", "OK");
		return resultMap;
	}
	
	
	@Scheduled(cron = "0 */45 * * * ?")
	public void systemGc(){
//		logger.error("系统定时GC开启!");
//		System.gc();
	}
}
