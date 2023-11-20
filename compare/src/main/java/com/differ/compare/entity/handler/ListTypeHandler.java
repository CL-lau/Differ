package com.differ.compare.entity.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/3 23:15
 */

public class ListTypeHandler extends BaseTypeHandler<List> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List parameter, JdbcType jdbcType) throws SQLException {
        // 将List属性序列化为字符串并设置到数据库中
        ps.setString(i, serialize(parameter));
    }

    @Override
    public List getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 从数据库中取出字符串并反序列化为List属性
        return deserialize(rs.getString(columnName));
    }

    @Override
    public List getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 从数据库中取出字符串并反序列化为List属性
        return deserialize(rs.getString(columnIndex));
    }

    @Override
    public List getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 从数据库中取出字符串并反序列化为List属性
        return deserialize(cs.getString(columnIndex));
    }

    private String serialize(List parameter) {
        // 将List属性序列化为字符串
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(parameter);
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to serialize list to JSON");
            return "";
        }
    }

    private List deserialize(String serialized) {
        // 将字符串反序列化为List属性
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(serialized, List.class);
        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to deserialize list from JSON");
            return new ArrayList();
        }
    }
}

