package com.mmall.service;

import java.util.List;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;

public interface SysUserService {
	public SysUser findByKeyword(String keyword);
	public void save(UserParam param);
	public void update(UserParam param) ;
	public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page);
	public List<SysUser> getAll();
	
}
