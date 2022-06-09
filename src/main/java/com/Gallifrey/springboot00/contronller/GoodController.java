package com.Gallifrey.springboot00.contronller;

import com.Gallifrey.springboot00.bean.Good;
import com.Gallifrey.springboot00.bean.Provider;
import com.Gallifrey.springboot00.bean.QueryInfo;
import com.Gallifrey.springboot00.mapper.GoodMapper;
import com.Gallifrey.springboot00.mapper.ProviderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController

public class GoodController {
    @Autowired
    GoodMapper Mapper;

    @RequestMapping("/getGood")
    public HashMap getList(QueryInfo queryInfo){

        //获取最大列表数和当前编号
        int numbers=Mapper.getCounts("%"+queryInfo.getQuery()+"%");
        int pageStart=(queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        //获得所有出库信息
       List<Good> providers = Mapper.getAllList("%"+queryInfo.getQuery()+"%",pageStart,queryInfo.getPageSize());

        HashMap<String ,Object> res=new HashMap<>();
        res.put("numbers",numbers);
//        System.out.println(providers);
        res.put("data",providers);

        return res;
    }

    //删除
    @RequestMapping("/deleteGood")
    public String deleteUser(String gno){
        int i=Mapper.delete(gno);
        return i>0?"success":"error";
    }

}