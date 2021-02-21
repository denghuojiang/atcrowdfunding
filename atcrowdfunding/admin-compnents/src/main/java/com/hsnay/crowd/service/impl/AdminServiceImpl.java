package com.hsnay.crowd.service.impl;

import com.hsnay.crowd.entity.Admin;
import com.hsnay.crowd.entity.AdminExample;
import com.hsnay.crowd.exception.LoginFailedException;
import com.hsnay.crowd.mapper.AdminMapper;
import com.hsnay.crowd.service.api.AdminService;
import com.hsnay.crowd.util.CrowdConstant;
import com.hsnay.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);

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
}
