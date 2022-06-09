package com.Gallifrey.springboot00.bean;

import lombok.Data;

import java.sql.Date;
@Data
public class OutStock {
    //出库实体类
        private String Ono;
        private String Cno;
        private String Gno;
        private String Cname;
        private String Ctel;
        private String Caddr;
        private String Gname;
        private Date Outdate;
        private String Outnum;
        private Double Outprice;
        private String Sno;
        private String Sname;


}
