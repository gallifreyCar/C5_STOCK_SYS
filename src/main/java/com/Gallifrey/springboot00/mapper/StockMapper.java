package com.Gallifrey.springboot00.mapper;

import com.Gallifrey.springboot00.bean.OutStock;
import com.Gallifrey.springboot00.bean.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StockMapper {

    public int getCounts(String name);

    public List<Stock> getAllStock(String name, int pageStart, int pageSize);
}
