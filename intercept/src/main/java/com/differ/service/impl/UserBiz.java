package com.differ.service.impl;

import com.differ.entity.User;
import com.differ.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/30 15:08
 */
@Service
public class UserBiz {

    @Autowired
    private UserMapper um;

    public User selectById(int id) {
        return um.selectById(id);
    }

    public List<User> queryAll() {
        return um.selectAll();
    }

    public Map<String, Object> queryByName1(String name) {
        return um.selectByName1(name);
    }

    public User queryByName2(String name) {
        return um.selectByName2(name);
    }

    public User queryByNameAndPwd(String name, String pwd) {
        return um.selectByNameAndPwd(name, pwd);
    }

    public boolean delete(int id) {
        return um.deleteById(id);
    }

    public boolean add(User user) {
        return um.insertUser(user.getName(), user.getSex(), user.getPwd(), user.getEmail());
    }

    public boolean change(String name, int id) {
        return um.updateById(name, id);
    }
}

