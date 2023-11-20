package com.differ.compare.entity.handler.typehandler;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/29 14:06
 */

import com.differ.compare.entity.enumer.ServiceType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceTypeHandler extends BaseTypeHandler<ServiceType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ServiceType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public ServiceType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        for (ServiceType type : ServiceType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

    @Override
    public ServiceType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        for (ServiceType type : ServiceType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

    @Override
    public ServiceType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        for (ServiceType type : ServiceType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}
