package com.Gallifrey.springboot00.bean;

import lombok.Data;

//管理人的实体类

@Data
public class Manager {
    private String Mno;// 编号
    private String Mname;//姓名
    private String Mpw;//密码
}
