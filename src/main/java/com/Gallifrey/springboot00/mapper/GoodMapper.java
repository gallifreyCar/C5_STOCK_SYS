package com.Gallifrey.springboot00.mapper;

import com.Gallifrey.springboot00.bean.Good;

import com.Gallifrey.springboot00.bean.InStock;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GoodMapper {
    public int getCounts(String name);


    public List<Good> getAllList(String name, int pageStart, int pageSize);

    @Delete("delete from goods where Gno=#{gno}")
    public int delete(String gno);

    @Insert("insert into goods (Gno,Gname,Tno) values(#{gno},#{gname},#{tno}")
    public int addGood(Good good);

    @Update("update goods set gno=#{gno},Gname=#{gname},Tno=#{tno}")
    public int editGood(Good good);
}
