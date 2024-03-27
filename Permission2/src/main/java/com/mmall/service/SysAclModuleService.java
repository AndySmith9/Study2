package com.mmall.service;

import com.mmall.param.AclModuleParam;

public interface SysAclModuleService {
	public void save(AclModuleParam param);
	public void update(AclModuleParam param);
	public void delete(int aclModuleId);
	
	
	
}
