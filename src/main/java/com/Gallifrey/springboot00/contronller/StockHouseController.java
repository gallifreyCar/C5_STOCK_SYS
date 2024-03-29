package com.Gallifrey.springboot00.contronller;

import com.Gallifrey.springboot00.bean.Provider;
import com.Gallifrey.springboot00.bean.QueryInfo;
import com.Gallifrey.springboot00.bean.StockHouse;
import com.Gallifrey.springboot00.mapper.ProviderMapper;
import com.Gallifrey.springboot00.mapper.StockHouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin
public class StockHouseController {
    @Autowired
    StockHouseMapper Mapper;

    @GetMapping("/getStockHouse")
    public HashMap getList(QueryInfo queryInfo){

        //获取最大列表数和当前编号
        int numbers=Mapper.getCounts("%"+queryInfo.getQuery()+"%");
        int pageStart=(queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        //获得所有出库信息
       List<StockHouse> providers = Mapper.getAllList("%"+queryInfo.getQuery()+"%",pageStart,queryInfo.getPageSize());

        HashMap<String ,Object> res=new HashMap<>();
        res.put("numbers",numbers);
//        System.out.println(providers);
        res.put("data",providers);

        return res;
    }

    //删除
    @PostMapping("/deleteStockHouse")
    public String deleteUser(String sno){
        int i=Mapper.delete(sno);
        return i>0?"success":"error";
    }

}