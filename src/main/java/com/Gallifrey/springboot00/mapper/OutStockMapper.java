package com.Gallifrey.springboot00.mapper;

import com.Gallifrey.springboot00.bean.OutStock;
import com.Gallifrey.springboot00.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OutStockMapper {


    public int getCounts(String Cname);

    public List<OutStock> getAllOutStock(String Cname, int pageStart, int pageSize);
}
