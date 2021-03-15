package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class test {
    @RequestMapping("/setSession")
    public String setSession(HttpServletRequest request){
        System.out.println("123456");
        request.getSession().setAttribute("1111","zhangsan");
        return "success";
    }
}
