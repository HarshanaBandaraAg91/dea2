package com.hms.restaurantservice.service;

import com.hms.restaurantservice.dto.MenuRequestDto;
import com.hms.restaurantservice.dto.MenuResponseDto;
import com.hms.restaurantservice.model.Menu;

import java.util.List;

public interface MenuService  {
    MenuResponseDto createMenu(MenuRequestDto request);
    MenuResponseDto updateMenu(Long id,MenuRequestDto menuUpdate);
    MenuResponseDto getMenuById(Long id );
    List<MenuResponseDto> getAllMenu();
    void deleteMenu(Long id);
    Menu findByName(String name);

}
