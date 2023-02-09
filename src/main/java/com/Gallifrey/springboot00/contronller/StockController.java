package com.Gallifrey.springboot00.contronller;

import com.Gallifrey.springboot00.bean.OutStock;
import com.Gallifrey.springboot00.bean.QueryInfo;
import com.Gallifrey.springboot00.bean.Stock;
import com.Gallifrey.springboot00.mapper.OutStockMapper;
import com.Gallifrey.springboot00.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin
public class StockController {
    @Autowired
    StockMapper stockMapper;

    @GetMapping("/getStock")
    public HashMap outStock(QueryInfo queryInfo){

        //获取最大列表数和当前编号
        int numbers=stockMapper.getCounts("%"+queryInfo.getQuery()+"%");
        int pageStart=(queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        //获得所有出库信息
        List<Stock> Stocks= stockMapper.getAllStock("%"+queryInfo.getQuery()+"%",pageStart,queryInfo.getPageSize());

        HashMap<String ,Object> res=new HashMap<>();
        res.put("numbers",numbers);
//        System.out.println(Stocks);
        res.put("data",Stocks);

        return res;
    }


    //删除
    @PostMapping("/deleteStock")
    public String deleteUser(String sno,String gno){
        int i=stockMapper.delete(sno,gno);
        return i>0?"success":"error";
    }
}