package com.mmall.dao;

import com.mmall.model.SysAclModule;
import com.mmall.model.SysAclModuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysAclModuleMapper {
    long countByExample(SysAclModuleExample example);

    int deleteByExample(SysAclModuleExample example);

//    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

//    int insertSelective(SysAclModule record);

    List<SysAclModule> selectByExample(SysAclModuleExample example);

//    SysAclModule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysAclModule record, @Param("example") SysAclModuleExample example);

    int updateByExample(@Param("record") SysAclModule record, @Param("example") SysAclModuleExample example);

//    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);
    
    
    
    
    List<SysAclModule> getAllAclModule();
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);
    int countByNameAndParentId1(@Param("parentId") Integer parentId, @Param("name") String name);
    SysAclModule selectByPrimaryKey(Integer id);
    int insertSelective(SysAclModule record);
    List<SysAclModule> getChildAclModuleListByLevel(@Param("level") String level);
    List<SysAclModule> getChildAclModuleListById(@Param("id") Integer id);
    void batchUpdateLevel(@Param("sysAclModuleList") List<SysAclModule> sysAclModuleList);
    void updateLevel(@Param("level") String level,@Param("id") Integer id);
    int updateByPrimaryKeySelective(SysAclModule record);
    int countByParentId(@Param("aclModuleId") int aclModuleId);
    int deleteByPrimaryKey(Integer id);
//    String getLevelById(@Param("id") Integer id);
    List<Integer> getIdListByAclModuleId(@Param("aclModuleId") int aclModuleId);
}