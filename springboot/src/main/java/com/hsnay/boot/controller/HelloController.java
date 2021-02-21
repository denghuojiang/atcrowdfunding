package com.hsnay.boot.controller;

import com.hsnay.boot.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//@ResponseBody
//@Controller
@RestController
@Slf4j
public class HelloController {
    @Autowired
    Person person;
    @RequestMapping("/hello")
    public String handlerHello(){
//        log.info("hello 请求");
        return "hello,spring boot中国";
    }
    @RequestMapping("/person")
    public String handlerPerson(){
        return person+"";
    }


}
