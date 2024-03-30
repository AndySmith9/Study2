package com.mmall.dao;

import com.mmall.model.SysRoleUser;
import com.mmall.model.SysRoleUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRoleUserMapper {
    long countByExample(SysRoleUserExample example);

    int deleteByExample(SysRoleUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    List<SysRoleUser> selectByExample(SysRoleUserExample example);

    SysRoleUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysRoleUser record, @Param("example") SysRoleUserExample example);

    int updateByExample(@Param("record") SysRoleUser record, @Param("example") SysRoleUserExample example);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);
    
    
    
    
    
    void deleteByRoleId(@Param("roleId") int roleId);
    void batchInsert(@Param("roleUserList") List<SysRoleUser> roleUserList);
    List<Integer> getUserIdListByRoleId(@Param("roleId") int roleId);
    List<Integer> getUserIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);
    List<Integer> getRoleIdListByUserId(@Param("userId") int userId);
    
}