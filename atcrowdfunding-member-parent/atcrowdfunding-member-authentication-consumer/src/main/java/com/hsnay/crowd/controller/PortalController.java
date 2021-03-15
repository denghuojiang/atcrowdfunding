package com.hsnay.crowd.controller;

import com.hsnay.crowd.api.MySQLRemoteService;
import com.hsnay.crowd.entity.vo.PortalTypeVO;
import com.hsnay.crowd.util.CrowdConstant;
import com.hsnay.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PortalController {
    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/")
    public String showPortalPage(ModelMap modelMap) {
        ResultEntity<List<PortalTypeVO>> portalTypeProjectData = mySQLRemoteService.getPortalTypeProjectData();
        String result = portalTypeProjectData.getResult();
        if (ResultEntity.SUCCESS.equals(result)) {
            List<PortalTypeVO> data = portalTypeProjectData.getData();
            modelMap.addAttribute("portal_data", data);
        }
        return "portal";
    }
}
