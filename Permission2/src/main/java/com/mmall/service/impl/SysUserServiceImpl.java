package com.mmall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysDept;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;
//import com.mmall.service.SysLogService;
import com.mmall.service.SysUserService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.MD5Util;
import com.mmall.util.PasswordUtil;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysDeptMapper sysDeptMapper;
//    @Resource
//    private SysLogService sysLogService;

    
    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }
    
    
    public boolean checkEmailExist(String mail, Integer userId) {
        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    public boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }
    public void save(UserParam param) {
        BeanValidator.check(param);
        if(checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if(checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        String password = PasswordUtil.randomPassword();
        //TODO:
        password = "12345678";
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .password(encryptedPassword).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
//        user.setOperator("system"); //TODO
//        user.setOperateIp("127.0.0.1");//TODO 
        user.setOperator(RequestHolder.getCurrentUser().getUsername());
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());

        // TODO: sendEmail

        sysUserMapper.insertSelective(user);
//        sysLogService.saveUserLog(null, user);
    }

    public void update(UserParam param) {
        BeanValidator.check(param);
        if(checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if(checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
//        after.setOperator("system"); //TODO
//        after.setOperateIp("127.0.0.1");//TODO 
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())); //TODO 
        after.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);
//        sysLogService.saveUserLog(before, after);
    }



    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page) {
        BeanValidator.check(page);
        
        
//        int count = sysUserMapper.countByDeptId(deptId);
//        if (count > 0) {
//            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, page);
//            return PageResult.<SysUser>builder().total(count).data(list).build();
//        }
        
        List<Integer> intList = getAllByDeptId(deptId);
        int count = sysUserMapper.countByDeptIdList(intList);
        if (count > 0) {
            List<SysUser> list = sysUserMapper.getPageByDeptIdList(intList, page);
            return PageResult.<SysUser>builder().total(count).data(list).build();
        }
        
        
//        int returnCount = 0;
//        List<SysUser> returnList = new ArrayList<SysUser>();
//        List<Integer> intList = getAllByDeptId(deptId);
//        for(Integer i:intList) {
//        	int count = sysUserMapper.countByDeptId(i);
//            if (count > 0) {
//                List<SysUser> list = sysUserMapper.getPageByDeptId(i, page);
//                returnCount+=count;
//                for(SysUser sysUser:list) {
//                	returnList.add(sysUser);
//                }
//            }
//        }
//        return PageResult.<SysUser>builder().total(returnCount).data(returnList).build();
        
        
        return PageResult.<SysUser>builder().build();
    }
    public List<Integer> getAllByDeptId(int deptId){
    	List<Integer> sysDeptIdList1 = new ArrayList<Integer>();
    	sysDeptIdList1.add(deptId);
    	List<SysDept> sysDeptList2 = sysDeptMapper.getChildDeptListById(deptId);
    	if(sysDeptList2 != null) {
    		for(SysDept sysDept:sysDeptList2) {
//    			sysDeptIdList1.add(sysDept.getId());
//    			getAllByDeptId(sysDept.getId());
    			    			
    			List<Integer> sysDeptIdList3 = getAllByDeptId(sysDept.getId());
//    			if(sysDeptIdList3 != null) {
    			for(Integer i:sysDeptIdList3) {
    				sysDeptIdList1.add(i);
    			}
//    		 }
    		}
    	}
    	
    	return sysDeptIdList1;
    }
    
    public List<SysUser> getAll() {
        return sysUserMapper.getAll();
    }

}
