package com.Gallifrey.springboot00.mapper;

import com.Gallifrey.springboot00.bean.InStock;
import com.Gallifrey.springboot00.bean.OutStock;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface InStockMapper {
    public int getCounts(String name);

    public List<InStock> getAllInStock(String name, int pageStart, int pageSize);

    @Delete("delete from inStock where Ono=#{ono}")
    public int delete(String ono);

}
