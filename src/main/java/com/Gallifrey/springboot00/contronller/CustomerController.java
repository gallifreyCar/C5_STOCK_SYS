package com.Gallifrey.springboot00.contronller;

import com.Gallifrey.springboot00.bean.Customer;
import com.Gallifrey.springboot00.bean.QueryInfo;
import com.Gallifrey.springboot00.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController

public class CustomerController {
    @Autowired
    CustomerMapper Mapper;

    @RequestMapping("/getCustomer")
    public HashMap getList(QueryInfo queryInfo){

        //获取最大列表数和当前编号
        int numbers=Mapper.getCounts("%"+queryInfo.getQuery()+"%");
        int pageStart=(queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        //获得所有出库信息
       List<Customer> customers = Mapper.getAllList("%"+queryInfo.getQuery()+"%",pageStart,queryInfo.getPageSize());

        HashMap<String ,Object> res=new HashMap<>();
        res.put("numbers",numbers);
//        System.out.println(customers);
        res.put("data",customers);

        return res;
    }


    //删除
    @RequestMapping("/deleteCustomer")
    public String deleteUser(String cno){
        int i=Mapper.delete(cno);
        return i>0?"success":"error";
    }
}