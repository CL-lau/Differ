package com.differ.entity.handler.typehandler;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/29 14:07
 */

import com.differ.entity.enumer.BodyType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BodyTypeHandler extends BaseTypeHandler<BodyType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BodyType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public BodyType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        for (BodyType type : BodyType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null; // Or throw an exception if a mapping is not found
    }

    @Override
    public BodyType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        for (BodyType type : BodyType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null; // Or throw an exception if a mapping is not found
    }

    @Override
    public BodyType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        for (BodyType type : BodyType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null; // Or throw an exception if a mapping is not found
    }
}
