package com.hsnay.crowd.mvc.controller;

import com.hsnay.crowd.entity.Role;
import com.hsnay.crowd.service.api.AdminService;
import com.hsnay.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AssignController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/assign/do/role/assign")
    public String saveAdminRoleRelationShip(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "roleIdList" ,required = false) List<Integer> roleIdList
    ) {
        adminService.saveAdminRoleRelationShip(adminId, roleIdList);
        return "redirect:/admin/get/page?pageNum=" + pageNum + "&keyword=" + keyword;

    }

    @RequestMapping("/assign/to/assign/role/page")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId,
                                   @RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("keyword") String keyword,
                                   ModelMap modelMap) {
        //查询已分配的角色
        List<Role> assignRoleList = roleService.getAssignRole(adminId);
        List<Role> unAssignRoleList = roleService.getUnAssignRole(adminId);
        modelMap.addAttribute("assignedRoleList", assignRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignRoleList);
        return "assign-role";
    }
}
