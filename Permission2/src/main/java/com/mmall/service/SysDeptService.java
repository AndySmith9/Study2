package com.mmall.service;



import com.mmall.param.DeptParam;


public interface SysDeptService {
	public void save(DeptParam param);
	public void update(DeptParam param);
	public void delete(int deptId);
		
}
