package com.differ.compare.utils;

import java.math.BigDecimal;
import java.sql.Types;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/7 13:55
 */
public class DBUtils {

    private static int getSqlType(String sqlTypeName) {
        if (sqlTypeName != null && sqlTypeName.contains("(") && sqlTypeName.split("\\(").length >= 1){
            sqlTypeName = sqlTypeName.split("\\(")[0];
        }
        int sqlType = switch (sqlTypeName.toUpperCase()) {
            case "BIT", "BOOLEAN" -> Types.BIT;
            case "TINYINT" -> Types.TINYINT;
            case "SMALLINT" -> Types.SMALLINT;
            case "INTEGER", "INT", "INT UNSIGNED" -> Types.INTEGER;
            case "BIGINT" -> Types.BIGINT;
            case "FLOAT" -> Types.FLOAT;
            case "DOUBLE" -> Types.DOUBLE;
            case "DECIMAL", "NUMERIC" -> Types.DECIMAL;
            case "CHAR", "VARCHAR", "TEXT", "LONGTEXT" -> Types.LONGVARCHAR;
            case "DATE" -> Types.DATE;
            case "TIME" -> Types.TIME;
            case "TIMESTAMP" -> Types.TIMESTAMP;
            case "BINARY", "VARBINARY", "BLOB", "LONGBLOB" -> Types.LONGVARBINARY;
            case "NULL" -> Types.NULL;
            default -> throw new IllegalArgumentException("Unsupported SQL type: " + sqlTypeName);
        };

        return sqlType;
    }

    public static Object convert(String value, int sqlType) {
        switch (sqlType) {
            case Types.BIT:
            case Types.BOOLEAN:
                return Boolean.valueOf(value);
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
            case Types.BIGINT:
                return Long.valueOf(value);
            case Types.FLOAT:
            case Types.DOUBLE:
                return Double.valueOf(value);
            case Types.DECIMAL:
            case Types.NUMERIC:
                return new BigDecimal(value);
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
                return value;
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                return java.sql.Timestamp.valueOf(value);
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                return value.getBytes();
            case Types.NULL:
                return null;
            default:
                throw new IllegalArgumentException("Unsupported SQL type: " + sqlType);
        }
    }
}
