package com.differ.compare.service.db;

import com.differ.compare.entity.db.ColumnInfo;
import com.differ.compare.entity.db.DatabaseInfo;
import com.differ.compare.entity.db.TableInfo;
import com.differ.compare.repository.db.mapper.ColumnInfoRepository;
import com.differ.compare.repository.db.mapper.DatabaseInfoRepository;
import com.differ.compare.repository.db.mapper.TableInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/3 19:12
 */
@Service
public class DatabaseLoaderService {
    private DatabaseInfoRepository databaseInfoRepository;
    private TableInfoRepository tableInfoRepository;
    private ColumnInfoRepository columnInfoRepository;

    @Autowired
    public void setInject(DatabaseInfoRepository databaseInfoRepository, TableInfoRepository tableInfoRepository, ColumnInfoRepository columnInfoRepository){
        this.databaseInfoRepository = databaseInfoRepository;
        this.tableInfoRepository = tableInfoRepository;
        this.columnInfoRepository = columnInfoRepository;
    }

    public boolean loadDatabaseInfo() {
        String url = "jdbc:mysql://localhost:3306/differ";
        String username = "root";
        String password = "root";

        DatabaseInfo databaseInfo = new DatabaseInfo();
        databaseInfo.setUrl(url);
        databaseInfo.setUsername(username);
        databaseInfo.setPassword(password);

        List<TableInfo> tables = loadTablesFromDatabase(url, username, password);
        databaseInfo.setTables(tables);

        return databaseInfoRepository.save(databaseInfo);
    }

    public boolean loadDatabaseInfo(DatabaseInfo databaseInfo) {
        String url = databaseInfo.getUrl();
        String username = databaseInfo.getUsername();
        String password = databaseInfo.getPassword();

        List<TableInfo> tables = loadTablesFromDatabase(url, username, password);
        databaseInfo.setTables(tables);

        return databaseInfoRepository.save(databaseInfo);
    }

    public boolean loadDatabaseInfo(String url, String username, String password) {
        List<TableInfo> tables = loadTablesFromDatabase(url, username, password);
        DatabaseInfo databaseInfo = new DatabaseInfo();
        databaseInfo.setTables(tables);

        return databaseInfoRepository.save(databaseInfo);
    }

    private List<TableInfo> loadTablesFromDatabase(String url, String username, String password) {
        List<TableInfo> tables = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            ResultSet tableResultSet = connection.getMetaData().getTables(null, null, null, new String[] {"TABLE"});

            while (tableResultSet.next()) {
                String tableName = tableResultSet.getString("TABLE_NAME");
                String tableDescription = tableResultSet.getString("REMARKS");

                TableInfo tableInfo = new TableInfo();
                tableInfo.setTableName(tableName);
                tableInfo.setDescription(tableDescription);

                List<ColumnInfo> columns = loadColumnsForTable(connection, tableName);
                tableInfo.setColumns(columns);
                if (this.tableInfoRepository.save(tableInfo)) tables.add(tableInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return tables;
    }

    private List<ColumnInfo> loadColumnsForTable(Connection connection, String tableName) {
        List<ColumnInfo> columns = new ArrayList<>();
        try {
            ResultSet columnResultSet = connection.getMetaData().getColumns(null, null, tableName, null);

            while (columnResultSet.next()) {
                String columnName = columnResultSet.getString("COLUMN_NAME");
                String columnType = columnResultSet.getString("TYPE_NAME");
                String columnDescription = columnResultSet.getString("REMARKS");

                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setColumnName(columnName);
                columnInfo.setType(columnType);
                columnInfo.setDescription(columnDescription);
                if (this.columnInfoRepository.save(columnInfo)) columns.add(columnInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return columns;
        }
        return columns;
    }
}
