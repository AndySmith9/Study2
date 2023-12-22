package com.mmall.controller;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmall.common.ApplicationContextHelper;
import com.mmall.common.JsonData;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.exception.ParamException;
import com.mmall.exception.PermissionException;
import com.mmall.model.SysAclModule;
import com.mmall.param.UserParam;
import com.mmall.service.TestService;
import com.mmall.util.BeanValidator;
import com.mmall.util.JsonMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

//	@Autowired
	@Resource
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
	
	
	@RequestMapping("/4.json")
	@ResponseBody
	//  @RequestParam从请求中获取.
	//  http://localhost:8081/Permission2/test/3?id3=5
	public JsonData selectUserById4(@RequestParam("id4") Integer id) {
		return JsonData.success(testService.selectByPrimaryKey(id),"使用JsonData作为与前端进行数据交互的json对象");
	}
	
	@RequestMapping("/5.page")
	@ResponseBody
	//  @RequestParam从请求中获取.
	//  http://localhost:8081/Permission2/test/3?id3=5
	public JsonData selectUserById5(@RequestParam("id5") Integer id) {
		throw new RuntimeException("");
//		return JsonData.success(testService.selectByPrimaryKey(id),"使用JsonData作为与前端进行数据交互的json对象");
	}
	
	@RequestMapping("/6.json")
	@ResponseBody
	//  @RequestParam从请求中获取.
	//  http://localhost:8081/Permission2/test/3?id3=5
	public JsonData selectUserById6(@RequestParam("id6") Integer id) {
		throw new PermissionException("权限不足");
//		return JsonData.success(testService.selectByPrimaryKey(id),"使用JsonData作为与前端进行数据交互的json对象");
	}

	@RequestMapping("/7.json")
	@ResponseBody
	//  @RequestParam从请求中获取.
	//  http://localhost:8081/Permission2/test/3?id3=5
	public JsonData selectUserById7(@RequestParam("id7") Integer id) {
		throw new RuntimeException("");
//		return JsonData.success(testService.selectByPrimaryKey(id),"使用JsonData作为与前端进行数据交互的json对象");
	}
	
	@RequestMapping("/8")
	@ResponseBody
	//  @RequestParam从请求中获取.
	//  http://localhost:8081/Permission2/test/3?id3=5
	public JsonData selectUserById8(@RequestParam(value="id8",required=false) Integer id) {
		throw new RuntimeException("");
//		return JsonData.success(testService.selectByPrimaryKey(id),"使用JsonData作为与前端进行数据交互的json对象");
	}
	
	
	
    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(UserParam userParam) throws ParamException {
        log.info("validate");
		
		
		  SysAclModuleMapper moduleMapper =
		  ApplicationContextHelper.popBean(SysAclModuleMapper.class); SysAclModule
		  module = moduleMapper.selectByPrimaryKey(1);
		  log.info(JsonMapper.obj2String(module));
		 
		 
        BeanValidator.check(userParam);
        return JsonData.success("test validate");
    }
	
	
}
