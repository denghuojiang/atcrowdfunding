package com.hsnay.crowd.mvc.controller;

import com.hsnay.crowd.entity.Admin;
import com.hsnay.crowd.service.api.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ControllerTest {
    @Autowired
    private AdminService adminService;

    @RequestMapping("test/ssm")
    public String testMvc(ModelMap modelMap){
        List<Admin> list=adminService.getAll();
        String s=null;
        System.out.println(s.length());
        System.out.println(list);
        modelMap.addAttribute("list",list);
        return "ssmTest";
    }
    @RequestMapping("send/array")
    public String testReceiveArrayOne(@RequestBody List<Integer> array){
        Logger logger = LoggerFactory.getLogger(ControllerTest.class);
        for (Integer i:array) {
            logger.info(i.toString());

        }
        return "ssmTest";
    }
}
