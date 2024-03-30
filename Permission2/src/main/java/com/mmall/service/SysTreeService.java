package com.mmall.service;

import java.util.List;

import com.mmall.dto.AclModuleLevelDto;
import com.mmall.dto.DeptLevelDto;


public interface SysTreeService {
	 public List<DeptLevelDto> deptTree();
	 public List<AclModuleLevelDto> aclModuleTree();
	 public List<AclModuleLevelDto> roleTree(int roleId);
}
