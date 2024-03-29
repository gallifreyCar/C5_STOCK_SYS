package com.Gallifrey.springboot00.contronller;

import com.Gallifrey.springboot00.bean.InStock;
import com.Gallifrey.springboot00.bean.OutStock;
import com.Gallifrey.springboot00.bean.QueryInfo;
import com.Gallifrey.springboot00.bean.User;
import com.Gallifrey.springboot00.mapper.OutStockMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin
public class OutStockController {
    @Autowired
    OutStockMapper outStockMapper;

    //查询
    @GetMapping("/getOutStock")
    public HashMap outStock(QueryInfo queryInfo){

        //获取最大列表数和当前编号
        int numbers=outStockMapper.getCounts("%"+queryInfo.getQuery()+"%");
        int pageStart=(queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        //获得所有出库信息
        List<OutStock> outStocks= outStockMapper.getAllOutStock("%"+queryInfo.getQuery()+"%",pageStart,queryInfo.getPageSize());

        HashMap<String ,Object> res=new HashMap<>();
        res.put("numbers",numbers);
//        System.out.println(outStocks);
        res.put("data",outStocks);

        return res;
    }
    //删除
    @PostMapping("/deleteOutStock")
    public String deleteUser(String id){
        int i=outStockMapper.delete(id);
        return i>0?"success":"error";
    }

    //增加
    @PostMapping("/addOutStock")
    public String addOne(@RequestBody OutStock outStock){
        int i=outStockMapper.addOutStock(outStock);
        return i>0?"success":"error";
    }

    //修改
    @PostMapping("/editOutStock")
    public String editOne(@RequestBody OutStock outStock){
        int i=outStockMapper.editOutStock(outStock);
        return i>0?"success":"error";
    }

}