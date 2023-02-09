package com.Gallifrey.springboot00.contronller;

import com.Gallifrey.springboot00.bean.MainMenu;
import com.Gallifrey.springboot00.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


//获得菜单


@RestController
@CrossOrigin
public class MenuController {

    @Autowired
    MenuMapper menuMapper;

    @GetMapping("/menus")
    public HashMap getAllMenus(){
        HashMap<String,Object> data=new HashMap<>();
        List<MainMenu> menus=menuMapper.getMenus();
        if(menus!=null){
            data.put("menus",menus);
            data.put("flag",200);
        }else {
            data.put("flag",404);
        }

        return data;
    }
}
