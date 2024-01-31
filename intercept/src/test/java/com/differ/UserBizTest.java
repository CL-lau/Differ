package com.differ;

import cn.hutool.core.lang.Assert;
import com.differ.entity.User;
import com.differ.service.impl.UserBiz;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/30 15:10
 */

//@SpringBootTest
@SpringBootTest
public class UserBizTest {
    @Autowired
    DataSource dataSource;
    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        //template模板，拿来即用
        connection.close();
    }

    @Autowired
    UserBiz userBiz;

    @Test
    public void selectByAccountAndPwd() {
        System.out.println(userBiz.selectById(2));
    }

    @Test
    public void queryAll() {
        System.out.println(userBiz.queryAll());
    }

    @Test
    public void queryByName() {
        long t1 = new Date().getTime();
        Map<String, Object> maps = userBiz.queryByName1("张三");
        long t2 = new Date().getTime();

        System.out.println(t2 - t1);
        System.out.println(maps);

        t1 = new Date().getTime();
        User user = userBiz.queryByName2("张三");
        t2 = new Date().getTime();
        System.out.println(t2 - t1);
        System.out.println(user);
    }

    @Test
    public void login() {
        User user = userBiz.queryByNameAndPwd("张三", "a");
        System.out.println(user);
        user = userBiz.queryByNameAndPwd("张三", "aa");
        System.out.println(user);
    }

    @Test
    public void delete() {
        Assert.isTrue(userBiz.delete(1));
    }

    @Test
    public void add() {
        User user = new User(null, "Test", "男", "aaaaa", "1234@qq.com");
        Assert.isTrue(userBiz.add(user));
    }
    @Test
    public void change() {
        Assert.isTrue(userBiz.change("测试",7),"修改失败");
    }
}

