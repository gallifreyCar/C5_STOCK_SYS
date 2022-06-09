package com.Gallifrey.springboot00.contronller;

import com.Gallifrey.springboot00.bean.Manager;
import com.Gallifrey.springboot00.bean.User;
import com.Gallifrey.springboot00.mapper.LoginMapper;
import com.Gallifrey.springboot00.mapper.UserMapper;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@CrossOrigin("http://localhost:8087")
@RestController
public class LoginController {



    @Autowired
    LoginMapper loginMapper;

    @RequestMapping("/login")
    public HashMap login(@RequestBody Manager manager){
            System.out.println(manager);    //打印一下传过来的信息

            Manager trueUser=loginMapper.getManager(manager); //拿到这个管理员信息
            String flag="error";

            System.out.println("Manager: "+trueUser);   //打印一下这个管理员信息
            HashMap<String,Object> res=new HashMap<>();


            //如果管理员存在，则返回成功和管理员信息
            if(trueUser!=null){
                flag="ok";
            }
            res.put("flag",flag);
            res.put("manager",trueUser);
            return res;
    }
}
