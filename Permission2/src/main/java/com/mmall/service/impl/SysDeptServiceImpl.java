package com.mmall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysDept;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
//import com.mmall.service.SysLogService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.LevelUtil;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;
//    @Resource
//    private SysLogService sysLogService;	
	
	
    
//    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
//        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
//    }
    private boolean checkExist1(Integer parentId, String deptName) {
        return sysDeptMapper.countByNameAndParentId1(parentId, deptName) > 0;
    }
    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }
	@Override
	public void save(DeptParam param) {
		// TODO Auto-generated method stub
        BeanValidator.check(param);
        if(checkExist1(param.getParentId(), param.getName())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept dept = SysDept.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();

        dept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
//        dept.setOperator("system"); //TODO
//        dept.setOperateIp("127.0.0.1");//TODO 
        dept.setOperator(RequestHolder.getCurrentUser().getUsername());
        dept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        dept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(dept);
//        sysLogService.saveDeptLog(null, dept);
	}

	
	
	
    @Transactional
    private void updateWithChild1(SysDept before, SysDept after) {
    	sysDeptMapper.updateByPrimaryKey(after);
        if (!after.getParentId().equals(before.getParentId())){
        	updateChildDeptLevel(after);
        }

    }
    private void updateChildDeptLevel(SysDept after) {
        List<SysDept> deptList = sysDeptMapper.getChildDeptListById(after.getId());
        if (CollectionUtils.isNotEmpty(deptList)) {
            for(SysDept sysDept:deptList) {
            	String level = LevelUtil.calculateLevel(getLevel(sysDept.getParentId()), sysDept.getParentId());
            	int id = sysDept.getId();
            	sysDeptMapper.updateLevel(level,id);
            	updateChildDeptLevel(sysDept);
            } 
        }
    }
    private List<Integer> getChildDeptIdListById(Integer id){
    	List<Integer> deptIdList1 = new ArrayList<Integer>();
    	deptIdList1.add(id);
        List<SysDept> deptList = sysDeptMapper.getChildDeptListById(id);
        if (CollectionUtils.isNotEmpty(deptList)) {
        	for(SysDept sysDept:deptList) {
        		List<Integer> deptIdList2 = getChildDeptIdListById(sysDept.getId());
        		for(Integer i:deptIdList2) {
        			deptIdList1.add(i);
        		}
        	}
        }
    	return deptIdList1;
    }
	@Override
	public void update(DeptParam param) {
		// TODO Auto-generated method stub
        BeanValidator.check(param);
        if(checkExist1(param.getParentId(), param.getName())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        List<Integer> idList = getChildDeptIdListById(param.getId());
        for(Integer i:idList) {
            if(i.equals(param.getParentId())) {
            	throw new ParamException("自己以及子节点不能作为自己的父节点");
            }
        }

        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");
//        if(checkExist1(param.getParentId(), param.getName())) {
//            throw new ParamException("同一层级下存在相同名称的部门");
//        }

        SysDept after = SysDept.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
//        after.setOperator("system"); //TODO
//        after.setOperateIp("127.0.0.1");//TODO 
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        updateWithChild1(before, after);
//        updateWithChild(before, after);
//        sysLogService.saveDeptLog(before, after);
	}
//    @Transactional
//    private void updateWithChild(SysDept before, SysDept after) {
//        String newLevelPrefix = after.getLevel();
//        String oldLevelPrefix = before.getLevel();
//        if (!after.getLevel().equals(before.getLevel())) {
//            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
//            if (CollectionUtils.isNotEmpty(deptList)) {
//                for (SysDept dept : deptList) {
//                    String level = dept.getLevel();
//                    if (level.indexOf(oldLevelPrefix) == 0) {
//                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
//                        dept.setLevel(level);
//                    }
//                }
//                sysDeptMapper.batchUpdateLevel(deptList);
//            }
//        }
//        sysDeptMapper.updateByPrimaryKey(after);
//    }

	
	@Override
	public void delete(int deptId) {
		// TODO Auto-generated method stub
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        Preconditions.checkNotNull(dept, "待删除的部门不存在，无法删除");
        if (sysDeptMapper.countByParentId(dept.getId()) > 0) {
            throw new ParamException("当前部门下面有子部门，无法删除");
        }
//        if(sysUserMapper.countByDeptId(dept.getId()) > 0) {
//            throw new ParamException("当前部门下面有用户，无法删除");
//        }
        sysDeptMapper.deleteByPrimaryKey(deptId);
	}

}
