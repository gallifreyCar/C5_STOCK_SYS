package com.Gallifrey.springboot00.mapper;


import com.Gallifrey.springboot00.bean.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LoginMapper {
    @Select("select * from manager where Mno=#{Mno} and Mpw=#{Mpw}")
    public Manager getManager(Manager manager);

}
