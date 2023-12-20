package com.mmall.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmall.dao.SysUserMapper;
import com.mmall.model.SysUser;
import com.mmall.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	SysUserMapper sysUserMapper;
	
	@Override
	public SysUser selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return sysUserMapper.selectByPrimaryKey(id);
	}

}
