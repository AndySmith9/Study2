package com.mmall.dao;

import com.mmall.beans.PageQuery;
import com.mmall.model.SysAcl;
import com.mmall.model.SysAclExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysAclMapper {
    long countByExample(SysAclExample example);

    int deleteByExample(SysAclExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

//    int insertSelective(SysAcl record);

    List<SysAcl> selectByExample(SysAclExample example);

//    SysAcl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysAcl record, @Param("example") SysAclExample example);

    int updateByExample(@Param("record") SysAcl record, @Param("example") SysAclExample example);

//    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);
    
    
    
    
    
    int insertSelective(SysAcl record);
    SysAcl selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysAcl record);
//    int countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("name") String name, @Param("id") Integer id);
    int countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("name") String name);
    int countByAclModuleId(@Param("aclModuleId") int aclModuleId);
    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("page") PageQuery page);
    List<Integer> getIdListByAclModuleId(@Param("aclModuleId") int aclModuleId);
    int countByAclModuleId1(@Param("aclIdList") List<Integer> aclIdList);
    List<SysAcl> getPageByAclModuleId1(@Param("aclIdList") List<Integer> aclIdList, @Param("page") PageQuery page);
    
    
}