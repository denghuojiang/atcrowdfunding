package com.hsnay.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String urlPath = "/auth/member/to/reg/page.html";
        String viewName = "member-reg";
        registry.addViewController(urlPath).setViewName(viewName);
        registry.addViewController("/auth/member/to/login/page.html").setViewName("member-login");
        registry.addViewController("/auth/member/to/center").setViewName("member-center");
        registry.addViewController("/auth/to/member/crowd/page.html").setViewName("member-crowd");
    }
}
