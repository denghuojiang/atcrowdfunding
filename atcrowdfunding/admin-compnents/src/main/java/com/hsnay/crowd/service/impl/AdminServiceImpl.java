package com.hsnay.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsnay.crowd.entity.Admin;
import com.hsnay.crowd.entity.AdminExample;
import com.hsnay.crowd.exception.LoginAcctAlreadyUseException;
import com.hsnay.crowd.exception.LoginAcctAlreadyUseForUpdateException;
import com.hsnay.crowd.exception.LoginFailedException;
import com.hsnay.crowd.mapper.AdminMapper;
import com.hsnay.crowd.service.api.AdminService;
import com.hsnay.crowd.util.CrowdConstant;
import com.hsnay.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public void saveAdmin(Admin admin) {
        //密码加密
        String pwsd = CrowdUtil.md5(admin.getUserPswd());
        //生成时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creatDate = simpleDateFormat.format(date);
        admin.setUserPswd(pwsd);
        admin.setCrateTime(creatDate);
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            if(e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String acct, String pwsd) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        //在criteria中封装查询条件
        criteria.andLoginAcctEqualTo(acct);
        List<Admin> list = adminMapper.selectByExample(adminExample);
        if (list == null || list.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_IN_FAILED);
        }
        if (list.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_LOGIN_USER_NAME_UNIQUE);
        }
        int index = 0;
        Admin admin = list.get(index);
        String userPswd = admin.getUserPswd();
        System.out.println(userPswd);
        if (!Objects.equals(CrowdUtil.md5(pwsd), userPswd)) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_IN_FAILED);
        }
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //pageHelper的非侵入设计 ---对原本查询不需要修改   （mybatis拦截器原理，修改sql然后访问数据库）
        List<Admin> list = adminMapper.selectAdminByKeyword(keyword);
        System.out.println(list);
        return new PageInfo<>(list);
    }

    @Override
    public void remove(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateAdmin(Admin admin) {
        //有选择的更新对null不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            throw new LoginAcctAlreadyUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
        }
    }

    @Override
    public Admin getAdminById(Integer id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }
}
