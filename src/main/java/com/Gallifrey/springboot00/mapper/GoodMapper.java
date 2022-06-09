package com.Gallifrey.springboot00.mapper;

import com.Gallifrey.springboot00.bean.Good;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GoodMapper {
    public int getCounts(String name);

    public List<Good> getAllList(String name, int pageStart, int pageSize);
}
