package com.hsnay.crowd.mvc.controller;

import com.hsnay.crowd.entity.Role;
import com.hsnay.crowd.service.api.AdminService;
import com.hsnay.crowd.service.api.AuthService;
import com.hsnay.crowd.service.api.RoleService;
import com.hsnay.crowd.util.ResultEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AssignController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;


    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth")
    public ResultEntity<String> saveRoleAuthRelationShip(
            @RequestBody Map<String, List<Integer>> map) {
        authService.saveRoleAuthRelationShip(map);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId) {
        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }

    @RequestMapping("/assign/do/role/assign")
    public String saveAdminRoleRelationShip(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList
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
