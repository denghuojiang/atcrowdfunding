package com.hsnay.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.hsnay.crowd.entity.Role;
import com.hsnay.crowd.service.api.RoleService;
import com.hsnay.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("/role/get/page")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        PageInfo<Role> pageInfo = null;
        try {
            pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
            return ResultEntity.successWithData(pageInfo);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/role/save", method = RequestMethod.POST)
    public ResultEntity<String> roleSave(Role role) {
        roleService.saveRole(role);

        return ResultEntity.successWithoutData();

    }

    @ResponseBody
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    public ResultEntity<String> roleUpdate(Role role) {
        roleService.update(role);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping(value = "/role/remove/by/role/id/array" ,method = RequestMethod.POST)
    public ResultEntity<String> roleRemoveByRoleIdArray(@RequestBody List<Integer> roleIdList) {
        roleService.removeRole(roleIdList);
        return ResultEntity.successWithoutData();
    }
}
