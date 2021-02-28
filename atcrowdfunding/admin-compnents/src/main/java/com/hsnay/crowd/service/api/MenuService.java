package com.hsnay.crowd.service.api;

import com.hsnay.crowd.entity.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Menu menu);
}
