package com.hsnay.crowd.mvc.controller;

import com.hsnay.crowd.entity.Admin;
import com.hsnay.crowd.service.api.AdminService;
import com.hsnay.crowd.util.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

}
