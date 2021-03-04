package com.hsnay.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.hsnay.crowd.entity.Admin;

import java.util.List;

public interface AdminService {

    public void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String acct, String pwsd);

    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

    void remove(Integer id);

    void updateAdmin(Admin admin);

    Admin getAdminById(Integer id);

    void saveAdminRoleRelationShip(Integer adminId, List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String username);
}
