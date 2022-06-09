package com.Gallifrey.springboot00.mapper;

import com.Gallifrey.springboot00.bean.Customer;
import com.Gallifrey.springboot00.bean.Provider;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProviderMapper {
    public int getCounts(String name);

    public List<Provider> getAllList(String name, int pageStart, int pageSize);
}
