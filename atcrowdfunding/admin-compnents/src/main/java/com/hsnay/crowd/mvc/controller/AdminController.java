package com.hsnay.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.hsnay.crowd.entity.Admin;
import com.hsnay.crowd.service.api.AdminService;
import com.hsnay.crowd.util.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/admin/do/login", method = RequestMethod.POST)
    public String doLogin(@RequestParam("loginAcct") String acct,
                          @RequestParam("userPswd") String pwsd,
                          HttpSession httpSession) {
        Admin admin = adminService.getAdminByLoginAcct(acct, pwsd);
        httpSession.setAttribute(CrowdConstant.ATTR_LOGIN_ADMIN, admin);

        return "redirect:/admin/to/main/page";
    }

    @RequestMapping(value = "/admin/do/logout", method = RequestMethod.GET)
    public String doLogOut(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/to/login/page";
    }

    @RequestMapping(value = "/admin/get/page", method = RequestMethod.GET)
    public String getPapeInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            Model model
    ) {
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        model.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    @RequestMapping({"/admin/remove/{adminId}/{pageNum}/{keyword}", "/admin/remove/{adminId}/{pageNum}"})
    public String adminRemove(@PathVariable("adminId") Integer id,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable(value = "keyword", required = false) String keyword,
                              HttpSession httpSession) {
        if (keyword == null) keyword = "";
        Admin admin = (Admin) httpSession.getAttribute(CrowdConstant.ATTR_LOGIN_ADMIN);
        //用户不能删除自己
        if (!Objects.equals(admin.getId(), id)) {
            adminService.remove(id);
        }

        return "redirect:/admin/get/page?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/admin/save")
    public String adminSave(Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page?pageNum="+Integer.MAX_VALUE;
    }


}
