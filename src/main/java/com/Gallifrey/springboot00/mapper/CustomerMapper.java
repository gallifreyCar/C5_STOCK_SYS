package com.Gallifrey.springboot00.mapper;

import com.Gallifrey.springboot00.bean.Customer;
import com.Gallifrey.springboot00.bean.InStock;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerMapper {
    public int getCounts(String name);

    public List<Customer> getAllList(String name, int pageStart, int pageSize);
}
