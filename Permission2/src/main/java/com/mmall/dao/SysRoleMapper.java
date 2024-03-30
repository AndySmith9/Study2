package com.mmall.dao;

import com.mmall.model.SysRole;
import com.mmall.model.SysRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMapper {
    long countByExample(SysRoleExample example);

    int deleteByExample(SysRoleExample example);

//    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

//    int insertSelective(SysRole record);

    List<SysRole> selectByExample(SysRoleExample example);

//    SysRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysRole record, @Param("example") SysRoleExample example);

    int updateByExample(@Param("record") SysRole record, @Param("example") SysRoleExample example);

//    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    
    
    
    int countByName(@Param("name") String name, @Param("id") Integer id);
    int insertSelective(SysRole record);
    SysRole selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysRole record);
    List<SysRole> getAll();
    int deleteByPrimaryKey(Integer id);
    
    
    
}