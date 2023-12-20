package com.mmall.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmall.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	TestService testService;
	
	@RequestMapping("/selectUserById")
	@ResponseBody
	public Object selectUserById() {
		return testService.selectByPrimaryKey(1);
	}
	
	@RequestMapping("/{id1}")
	@ResponseBody
	// @PathVariable从路径中获取
	// http://localhost:8081/Permission2/test/4
   public Object selectUserById2(@PathVariable("id1") Integer id) {
	   return testService.selectByPrimaryKey(id);
   }
	
	@RequestMapping("/3")
	@ResponseBody
	//  @RequestParam从请求中获取.
	//  http://localhost:8081/Permission2/test/3?id3=5
	public Object selectUserById3(@RequestParam("id3") Integer id) {
		return testService.selectByPrimaryKey(id);
	}
	

}
