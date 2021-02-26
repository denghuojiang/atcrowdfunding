package com.hsnay.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.hsnay.crowd.entity.Role;

import java.util.List;

public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum , Integer pageSize , String keyword);

    void saveRole(Role role);

    void update(Role role);

    void removeRole(List<Integer> roleList);
}
