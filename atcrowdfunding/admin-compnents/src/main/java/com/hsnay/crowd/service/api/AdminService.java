package com.hsnay.crowd.service.api;

import com.hsnay.crowd.entity.Admin;

import java.util.List;

public interface AdminService {

    public void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String acct, String pwsd);
}
