package com.hsnay.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsnay.crowd.entity.Role;
import com.hsnay.crowd.mapper.RoleMapper;
import com.hsnay.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roles = roleMapper.selectRoleByKeyword(keyword);
        return new PageInfo<>(roles);
    }
}
