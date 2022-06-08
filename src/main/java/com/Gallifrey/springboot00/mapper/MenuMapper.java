package com.Gallifrey.springboot00.mapper;

import com.Gallifrey.springboot00.bean.MainMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MenuMapper {

    public List<MainMenu> getMenus();
}
