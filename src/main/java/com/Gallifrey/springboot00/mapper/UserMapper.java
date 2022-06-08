package com.Gallifrey.springboot00.mapper;

import com.Gallifrey.springboot00.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
//   查询一个用户全部信息
    //@Select("select *from user where username=#{username,jdbcType=VARCHAR} and password=#{password,jdbcType=VARCHAR}")
    //这里方法太多了，还是使用xml文件比较好
    public User getInfo(User user);
//   获取所有用户 通过用户名或者昵称
    public List<User> getAllUser(String username,int pageStart,int pageSize);
//    获取所有用户个数
    public int getUserCounts(String username);

//    状态更新
    public int updateState(int id,Boolean state);

//    增加用户
    public int addUser(User user);

//    删除数据
    public int deleteUser(int id);

//    获得修改的User
    public User getUpdateUser(int id);

//    修改用户
    public int editUser(User user);
}
