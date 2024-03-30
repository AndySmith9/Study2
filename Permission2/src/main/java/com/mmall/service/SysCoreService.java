package com.mmall.service;

import java.util.List;

import com.mmall.model.SysAcl;

public interface SysCoreService {
	 public List<SysAcl> getCurrentUserAclList();
	 public List<SysAcl> getRoleAclList(int roleId);
	 public boolean hasUrlAcl(String url);
	
	
	
}
