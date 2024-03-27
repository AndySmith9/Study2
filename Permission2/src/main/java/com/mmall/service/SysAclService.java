package com.mmall.service;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.model.SysAcl;
import com.mmall.param.AclParam;

public interface SysAclService {
	 public void save(AclParam param);
	 public void update(AclParam param);
	 public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page);
	 
}



