package com.hsnay.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.hsnay.crowd.entity.Role;

public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum , Integer pageSize , String keyword);
}
