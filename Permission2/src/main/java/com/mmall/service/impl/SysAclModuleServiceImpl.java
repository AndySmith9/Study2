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
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysAclModule;
import com.mmall.param.AclModuleParam;
import com.mmall.service.SysAclModuleService;
//import com.mmall.service.SysLogService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.LevelUtil;
@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
//    @Resource
//    private SysAclMapper sysAclMapper;
//    @Resource
//    private SysLogService sysLogService;

    
    private boolean checkExist(Integer parentId, String aclModuleName, Integer deptId) {
//        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, deptId) > 0;
        return sysAclModuleMapper.countByNameAndParentId1(parentId, aclModuleName) > 0;
    }

    private String getLevel(Integer aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getLevel();
    }
    
    public void save(AclModuleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
        SysAclModule aclModule = SysAclModule.builder().name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
                .status(param.getStatus()).remark(param.getRemark()).build();
        aclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperateTime(new Date());
        sysAclModuleMapper.insertSelective(aclModule);
//        sysLogService.saveAclModuleLog(null, aclModule);
    }

    
    
  //public List<Integer> getChildAclModuleListById(Integer id){
    private List<SysAclModule> getChildAclModuleListById(Integer id){	
    	return sysAclModuleMapper.getChildAclModuleListById(id);
//    	List<Integer> idList = new ArrayList<Integer>();
//    	List<SysAclModule> aclModuleList = sysAclModuleMapper.getChildAclModuleListById(id);
//    	if(aclModuleList != null) {
//    		for(SysAclModule sysAclModule:aclModuleList) {
//    			idList.add(sysAclModule.getId());
//    		}
//    	}
//    	return idList;
    }
    private List<Integer> getChildAclModuleIdListById(Integer id){
    	List<Integer> idList = new ArrayList<Integer>();
    	idList.add(id);
    	List<SysAclModule> childAclModuleList = getChildAclModuleListById(id);
    	if (CollectionUtils.isNotEmpty(childAclModuleList)) {
    		for(SysAclModule sysAclModule:childAclModuleList) {
    			List<Integer> idList2 = getChildAclModuleIdListById(sysAclModule.getId());
    			for(Integer i:idList2) {
    				idList.add(i);
    			}
    		}
    	}
    	return idList;
    }
    private void updateChildLevel(SysAclModule after) {
    	List<SysAclModule> childAclModuleList = getChildAclModuleListById(after.getId());
    	if (CollectionUtils.isNotEmpty(childAclModuleList)) {
    		for(SysAclModule sysAclModule:childAclModuleList) {
    			String level = LevelUtil.calculateLevel(getLevel(sysAclModule.getParentId()), sysAclModule.getParentId());
    			sysAclModuleMapper.updateLevel(level, sysAclModule.getId());
    			updateChildLevel(sysAclModule);
    		}
    	}
    }
    @Transactional
    private void updateWithChild(SysAclModule before, SysAclModule after) {
    	sysAclModuleMapper.updateByPrimaryKeySelective(after);
        if (!after.getParentId().equals(before.getParentId())) {
        	updateChildLevel(after);
        }  
    }  
    public void update(AclModuleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
//        String level = sysAclModuleMapper.getLevelById(param.getId());
//        String parentLevel = sysAclModuleMapper.getLevelById(param.getParentId());
//        if(parentLevel!=null&&parentLevel.indexOf(level)==0) {
//        	throw new ParamException("自己以及自己子节点不能作为自己的父节点");
//        }
        List<Integer> childIdList = getChildAclModuleIdListById(param.getId());
        for(Integer i:childIdList) {
        	if(param.getParentId().equals(i)) {
        		throw new ParamException("自己以及自己子节点不能作为自己的父节点");
        	}
        }
        
//        if(param.getParentId().equals(param.getId())) {
//        	throw new ParamException("自己不能作为自己的父节点");
//        }
//        List<SysAclModule> childAclModuleList = getChildAclModuleListById(param.getId());
//        for(SysAclModule sysAclModule:childAclModuleList) {
//            if(param.getParentId().equals(sysAclModule.getId())) {
//            	throw new ParamException("自己的子节点不能作为自己的父节点");
//            }
//        }

        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");

        SysAclModule after = SysAclModule.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
                .status(param.getStatus()).remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        updateWithChild(before, after);
//        sysLogService.saveAclModuleLog(before, after);
    }

//    @Transactional
//    private void updateWithChild(SysAclModule before, SysAclModule after) {
//        String newLevelPrefix = after.getLevel();
//        String oldLevelPrefix = before.getLevel();
//        if (!after.getLevel().equals(before.getLevel())) {
//            List<SysAclModule> aclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(before.getLevel());
//            if (CollectionUtils.isNotEmpty(aclModuleList)) {
//                for (SysAclModule aclModule : aclModuleList) {
//                    String level = aclModule.getLevel();
//                    if (level.indexOf(oldLevelPrefix) == 0) {
//                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
//                        aclModule.setLevel(level);
//                    }
//                }
//                sysAclModuleMapper.batchUpdateLevel(aclModuleList);
//            }
//        }
//        sysAclModuleMapper.updateByPrimaryKeySelective(after);
//    }


    

    public void delete(int aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        Preconditions.checkNotNull(aclModule, "待删除的权限模块不存在，无法删除");
        if(sysAclModuleMapper.countByParentId(aclModule.getId()) > 0) {
            throw new ParamException("当前模块下面有子模块，无法删除");
        }
//        if (sysAclMapper.countByAclModuleId(aclModule.getId()) > 0) {
//            throw new ParamException("当前模块下面有用户，无法删除");
//        }
        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
    }

}
