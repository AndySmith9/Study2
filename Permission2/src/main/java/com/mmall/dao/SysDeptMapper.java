package com.mmall.dao;

import com.mmall.model.SysDept;
import com.mmall.model.SysDeptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysDeptMapper {
    long countByExample(SysDeptExample example);

    int deleteByExample(SysDeptExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

//    int insertSelective(SysDept record);

    List<SysDept> selectByExample(SysDeptExample example);

//    SysDept selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysDept record, @Param("example") SysDeptExample example);

    int updateByExample(@Param("record") SysDept record, @Param("example") SysDeptExample example);

    int updateByPrimaryKeySelective(SysDept record);

//    int updateByPrimaryKey(SysDept record);
    
    
    
//    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);
    int countByNameAndParentId1(@Param("parentId") Integer parentId, @Param("name") String name);
    SysDept selectByPrimaryKey(Integer id);
    int insertSelective(SysDept record);
    List<SysDept> getChildDeptListByLevel(@Param("level") String level);
    void batchUpdateLevel(@Param("sysDeptList") List<SysDept> sysDeptList);
    void updateLevel(@Param("level") String level,@Param("id") int id);
    int updateByPrimaryKey(SysDept record);
    int countByParentId(@Param("deptId") int deptId);
    List<SysDept> getAllDept();
    List<SysDept> getChildDeptListById(@Param("id") int id);
}