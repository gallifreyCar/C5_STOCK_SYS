package com.Gallifrey.springboot00.contronller;

import com.Gallifrey.springboot00.bean.InStock;
import com.Gallifrey.springboot00.bean.OutStock;
import com.Gallifrey.springboot00.bean.QueryInfo;
import com.Gallifrey.springboot00.mapper.InStockMapper;
import com.Gallifrey.springboot00.mapper.OutStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController

public class InStockController {
    @Autowired
    InStockMapper inStockMapper;

    @RequestMapping("/getInStock")
    public HashMap inStock(QueryInfo queryInfo){

        //获取最大列表数和当前编号
        int numbers=inStockMapper.getCounts("%"+queryInfo.getQuery()+"%");
        int pageStart=(queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        //获得所有出库信息
        List<InStock> inStocks= inStockMapper.getAllInStock("%"+queryInfo.getQuery()+"%",pageStart,queryInfo.getPageSize());

        HashMap<String ,Object> res=new HashMap<>();
        res.put("numbers",numbers);
        System.out.println(inStocks);
        res.put("data",inStocks);

        return res;
    }

}