package com.hsnay.crowd.mvc.controller;

import com.hsnay.crowd.entity.Menu;
import com.hsnay.crowd.service.api.MenuService;
import com.hsnay.crowd.util.ResultEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/menu/remove")
    public ResultEntity<String> removeMenu(Menu menu){
        menuService.removeMenu(menu);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/menu/update")
    public ResultEntity<String> updateMenu(Menu menu){
        menuService.updateMenu(menu);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping(value = "/menu/save")
    public ResultEntity<String> saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return  ResultEntity.successWithoutData();
    }

    @RequestMapping(value = "/menu/get/whole/treeNew" )
    public ResultEntity<Menu> getWholeTreeNew() {
        Menu root = null;
        HashMap<Integer, Menu> map = new HashMap<>();
        List<Menu> menuList = menuService.getAll();
        for (Menu menu : menuList) {
            Integer id = menu.getId();
            map.put(id,menu);
        }
        for (Menu menu : menuList) {
            Integer pid = menu.getPid();
            if(pid == null){
                root = menu;
                continue;
            }
            Menu father = map.get(pid);
            father.getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }

    @RequestMapping("/menu/get/whole/tree")
    public ResultEntity<Menu> getWholeTree() {
        List<Menu> menuList = menuService.getAll();
        Menu root = null;
        for (Menu menu : menuList) {
            Integer pid = menu.getPid();
            if (pid == null) {
                root = menu;
                continue;
            }
            //找到pid进入组装
            for (Menu maybeFather : menuList) {
                Integer id = maybeFather.getId();
                if (Objects.equals(id, pid)) {
                    maybeFather.getChildren().add(menu);
                    break;
                }
            }
        }
        return ResultEntity.successWithData(root);
    }
}
