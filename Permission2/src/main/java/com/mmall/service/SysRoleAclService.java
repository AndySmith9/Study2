package com.mmall.service;

import java.util.List;

public interface SysRoleAclService {
	public void changeRoleAcls(Integer roleId, List<Integer> aclIdList);
}
