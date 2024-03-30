package com.mmall.service;

import java.util.List;

import com.mmall.model.SysRole;
import com.mmall.param.RoleParam;

public interface SysRoleService {
	public void save(RoleParam param);
	public void update(RoleParam param);
	public List<SysRole> getAll();
	public void delete(int id);
	
	
}
