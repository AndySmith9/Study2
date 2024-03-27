package com.mmall.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysAcl;
import com.mmall.param.AclParam;
import com.mmall.service.SysAclService;
//import com.mmall.service.SysLogService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;

@Service
public class SysAclServiceImpl implements SysAclService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
//    @Resource
//    private SysLogService sysLogService;

    
    public boolean checkExist(int aclModuleId, String name, Integer id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name) > 0;
    }
    public String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int)(Math.random() * 100);
    }
    public void save(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl acl = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        acl.setCode(generateCode());
        acl.setOperator(RequestHolder.getCurrentUser().getUsername());
        acl.setOperateTime(new Date());
        acl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclMapper.insertSelective(acl);
//        sysLogService.saveAclLog(null, acl);
    }

    public void update(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限点不存在");

        SysAcl after = SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateTime(new Date());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));

        sysAclMapper.updateByPrimaryKeySelective(after);
//        sysLogService.saveAclLog(before, after);
    }

   private List<Integer> getAclIdList(int aclModuleId){
    	List<Integer> aclIdList1 = new ArrayList<Integer>();
    	aclIdList1.add(aclModuleId);
    	List<Integer> aclIdList2 = sysAclModuleMapper.getIdListByAclModuleId(aclModuleId);
    	if (CollectionUtils.isNotEmpty(aclIdList2)) {
    		for(Integer i:aclIdList2) {
    			List<Integer> aclIdList3 = getAclIdList(i);
    			for(Integer i3:aclIdList3) {
    				aclIdList1.add(i3);
    			}
    		}
    	}
    	return aclIdList1;
    }
    
    
    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page) {
        BeanValidator.check(page);
        
        
//        int count = sysAclMapper.countByAclModuleId(aclModuleId);
//        if (count > 0) {
//            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, page);
//            return PageResult.<SysAcl>builder().data(aclList).total(count).build();
//        }
        
        List<Integer> idList = getAclIdList(aclModuleId);
        int count = sysAclMapper.countByAclModuleId1(idList);
        if (count > 0) {
            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId1(idList, page);
            return PageResult.<SysAcl>builder().data(aclList).total(count).build();
        }
        
        return PageResult.<SysAcl>builder().build();
    }

}
