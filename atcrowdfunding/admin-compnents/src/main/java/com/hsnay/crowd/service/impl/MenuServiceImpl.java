package com.hsnay.crowd.service.impl;

import com.hsnay.crowd.entity.MenuExample;
import com.hsnay.crowd.entity.Menu;
import com.hsnay.crowd.mapper.MenuMapper;
import com.hsnay.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void removeMenu(Menu menu) {

        menuMapper.deleteByPrimaryKey(menu.getId());
    }
}
