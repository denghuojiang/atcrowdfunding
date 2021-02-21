package com.hsnay.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.hsnay.crowd.entity.Admin;
import com.hsnay.crowd.service.api.AdminService;
import com.hsnay.crowd.util.CrowdConstant;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/admin/do/login" ,method = RequestMethod.POST)
        public String doLogin(@RequestParam("loginAcct") String acct ,
                              @RequestParam("userPswd") String pwsd ,
                              HttpSession httpSession){
                Admin admin = adminService.getAdminByLoginAcct(acct,pwsd);
                httpSession.setAttribute(CrowdConstant.ATTR_LOGIN_ADMIN,admin);

        return "redirect:/admin/to/main/pape";
    }

    @RequestMapping(value = "/admin/do/logout",method = RequestMethod.GET)
    public String doLogOut(HttpSession session){
        session.invalidate();
        return "redirect:/admin/to/login/page";
    }

    @RequestMapping(value = "/admin/get/page",method = RequestMethod.GET)
    public String getPapeInfo(
            @RequestParam(value = "keyword" ,defaultValue = "") String keyword,
            @RequestParam(value = "papeNum",defaultValue = "1") Integer papeNum,
            @RequestParam(value = "papeSize",defaultValue = "5") Integer papeSize,
            Model model
    ){
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, papeNum, papeSize);
        model.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin-page" ;
    }


}
