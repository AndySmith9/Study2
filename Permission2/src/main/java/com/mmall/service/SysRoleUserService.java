package com.mmall.service;

import java.util.List;

import com.mmall.model.SysUser;

public interface SysRoleUserService {
	public void changeRoleUsers(int roleId, List<Integer> userIdList);
	public List<SysUser> getListByRoleId(int roleId);
	
}
