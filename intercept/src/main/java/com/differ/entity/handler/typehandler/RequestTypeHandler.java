package com.differ.entity.handler.typehandler;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/29 14:07
 */

import com.differ.entity.enumer.RequestType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestTypeHandler extends BaseTypeHandler<RequestType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RequestType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public RequestType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        for (RequestType type : RequestType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null; // Or throw an exception if a mapping is not found
    }

    @Override
    public RequestType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        for (RequestType type : RequestType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null; // Or throw an exception if a mapping is not found
    }

    @Override
    public RequestType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        for (RequestType type : RequestType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null; // Or throw an exception if a mapping is not found
    }
}
