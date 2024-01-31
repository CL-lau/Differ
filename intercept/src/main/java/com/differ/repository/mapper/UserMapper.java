package com.differ.repository.mapper;

import com.differ.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/30 15:05
 */
@Mapper
@Repository
public interface UserMapper {
    @Select("select * from user")
    List<User> selectAll();

    @Select("select * from user where id = #{id}")
    User selectById(@Param("id") int id);

    @Select("select * from user where name = #{name}")
    Map<String, Object> selectByName1(@Param("name") String name);

    @Select("select * from user where name = #{name}")
    User selectByName2(@Param("name") String name);

//    @Select("select * from user where name = #{name} and pwd = #{name}")
//    User selectByNameAndPwd(@Param("name") String name, @Param("pwd") String pwd);

    @Select("select * from user where name = #{name} and pwd = #{pwd}")
    User selectByNameAndPwd(String name, String pwd);

    @Delete("delete from user where id = #{id}")
    boolean deleteById(int id);

    @Insert("insert into user values (null,#{name},#{sex},#{pwd},#{email})")
    boolean insertUser(String name, String sex, String pwd, String email);

    @Update("update user set name =  #{name} where id = #{id}")
    boolean updateById(String name, int id);
}

