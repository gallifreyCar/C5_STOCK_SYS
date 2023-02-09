package com.Gallifrey.springboot00.bean;

import lombok.Data;

@Data
public class QueryInfo {
     private String query;// 查询信息 username
     private int pageNum=1;// 当前页
     private int pageSize=3;//每页最大数


}
