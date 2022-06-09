package com.Gallifrey.springboot00.bean;

import lombok.Data;

import java.sql.Date;

@Data
public class InStock {
    //入库实体类

        private String Pno;
        private String Gno;
        private String Pname;
        private String Ptel;
        private String Paddr;
        private String Gname;
        private Date Indate;
        private String Innum;
        private Double Inprice;
        private String Sno;
        private String Sname;


}
