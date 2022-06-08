package com.Gallifrey.springboot00.bean;

import lombok.*;
import org.apache.ibatis.annotations.Select;

//主菜单
@Data
public class User {
    private int id;
    private String name;//昵称
    private String email;//邮箱
    private int flowerId;//花
    private String username;//用户名
    private String password;//密码
    private boolean state;//状态

}
