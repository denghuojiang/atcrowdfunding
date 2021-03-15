package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class test {
    //获取Session值
    @RequestMapping("/getSession")
    public String getSession(HttpServletRequest request){
        System.out.println("123456");
        return (String)request.getSession().getAttribute("1111");
    }
}
