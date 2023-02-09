package com.Gallifrey.springboot00.mapper;

import com.Gallifrey.springboot00.bean.InStock;
import com.Gallifrey.springboot00.bean.OutStock;
import com.Gallifrey.springboot00.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OutStockMapper {

    @Delete("delete from outStock where Ono=#{id}")
    public int delete(String id);

    @Insert("insert into outStock (Ono,Gno,Cno,Outnum,Outdate,Outprice,Sno) values(#{ono},#{gno},#{cno},#{outnum},#{outdate},#{outprice},#{sno})")
    public int addOutStock(OutStock outStock);

    @Update("update outStock set Gno=#{gno},Cno=#{cno},Outnum=#{outnum},Outdate=#{outdate},Outprice=#{outprice},Sno=#{sno} where Ono=#{Ono}")
    public int editOutStock( OutStock outStock);

    public int getCounts(String Cname);

    public List<OutStock> getAllOutStock(String Cname, int pageStart, int pageSize);
}
