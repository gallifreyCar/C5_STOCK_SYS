package com.Gallifrey.springboot00.mapper;

import com.Gallifrey.springboot00.bean.Provider;
import com.Gallifrey.springboot00.bean.StockHouse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StockHouseMapper {
    public int getCounts(String name);

    public List<StockHouse> getAllList(String name, int pageStart, int pageSize);
}
