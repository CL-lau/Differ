package com.differ.repository.mapper;

import com.differ.entity.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/30 16:16
 */
@Mapper
@Repository
public interface UserLoginMapper {
    //查询
    public List<UserLogin> queryAll();
    //添加数据
    public int add(UserLogin userLogin);
    //根据用户名查询数据
    public UserLogin queryByName(String username);
}
