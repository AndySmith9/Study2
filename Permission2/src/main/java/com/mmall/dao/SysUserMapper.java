package com.mmall.dao;

import com.mmall.beans.PageQuery;
import com.mmall.model.SysUser;
import com.mmall.model.SysUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    long countByExample(SysUserExample example);

    int deleteByExample(SysUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

//    int insertSelective(SysUser record);

    List<SysUser> selectByExample(SysUserExample example);

//    SysUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserExample example);

//    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    
    
    
    
    SysUser findByKeyword(@Param("keyword") String keyword);
    int countByMail(@Param("mail") String mail, @Param("id") Integer id);
    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);
    int insertSelective(SysUser record);
    SysUser selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysUser record);
    int countByDeptId(@Param("deptId") int deptId);
    List<SysUser> getPageByDeptId(@Param("deptId") int deptId, @Param("page") PageQuery page);
    int countByDeptIdList(@Param("deptIdList") List<Integer> deptIdList);
    List<SysUser> getPageByDeptIdList(@Param("deptIdList") List<Integer> deptIdList, @Param("page") PageQuery page);
    List<SysUser> getAll();
}