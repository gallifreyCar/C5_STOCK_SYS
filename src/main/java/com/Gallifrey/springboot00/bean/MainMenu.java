package com.Gallifrey.springboot00.bean;
import lombok.Data;
import java.util.List;

//主菜单
@Data
public class MainMenu {
    private int id;
    private String title;
    private String path;
    private List<SubMenu> subMenuList;

}
