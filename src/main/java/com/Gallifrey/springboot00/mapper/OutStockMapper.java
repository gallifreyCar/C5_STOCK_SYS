package com.Gallifrey.springboot00.mapper;

import com.Gallifrey.springboot00.bean.OutStock;
import com.Gallifrey.springboot00.bean.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OutStockMapper {

    @Delete("delete from outStock where Ono=#{id}")
    public int delete(String id);


    public int getCounts(String Cname);

    public List<OutStock> getAllOutStock(String Cname, int pageStart, int pageSize);
}
