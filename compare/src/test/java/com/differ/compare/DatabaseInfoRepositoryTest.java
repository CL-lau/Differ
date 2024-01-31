package com.differ.compare;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/3 23:41
 */

import com.differ.compare.entity.db.ColumnInfo;
import com.differ.compare.entity.db.DatabaseInfo;
import com.differ.compare.entity.db.TableInfo;
import com.differ.compare.repository.db.mapper.ColumnInfoRepository;
import com.differ.compare.repository.db.mapper.DatabaseInfoRepository;
import com.differ.compare.repository.db.mapper.TableInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DatabaseInfoRepositoryTest {

    @Autowired
    private DatabaseInfoRepository databaseInfoRepository;

    @Autowired
    private TableInfoRepository tableInfoRepository;

    @Autowired
    private ColumnInfoRepository columnInfoRepository;

    @Test
    public void testDatabaseInfoOperations() {
        // Create a DatabaseInfo object
        DatabaseInfo databaseInfo = new DatabaseInfo();
        databaseInfo.setDatabaseName("Test Database");
        databaseInfo.setUrl("jdbc:mysql://localhost:3306/test_db");
        databaseInfo.setUsername("test_user");
        databaseInfo.setPassword("test_password");

        // Create TableInfo and ColumnInfo objects
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName("test_table");
        tableInfo.setDescription("Test Table");

        ColumnInfo columnInfo1 = new ColumnInfo();
        columnInfo1.setColumnName("id");
        columnInfo1.setType("INT");
        columnInfo1.setDescription("ID Column");

        ColumnInfo columnInfo2 = new ColumnInfo();
        columnInfo2.setColumnName("name");
        columnInfo2.setType("VARCHAR");
        columnInfo2.setDescription("Name Column");

        List<ColumnInfo> columns = new ArrayList<>();
        columns.add(columnInfo1);
        columns.add(columnInfo2);

        tableInfo.setColumns(columns);

        List<TableInfo> tables = new ArrayList<>();
        tables.add(tableInfo);

        databaseInfo.setTables(tables);

        // Save the DatabaseInfo object and related data
        databaseInfoRepository.save(databaseInfo);
        System.out.println(databaseInfo.getId());
        // Retrieve the saved DatabaseInfo
        DatabaseInfo retrievedDatabaseInfo = databaseInfoRepository.findById(1l);
        System.out.println("Retrieved DatabaseInfo: " + retrievedDatabaseInfo);

        // Verify the retrieved data
//        assert retrievedDatabaseInfo != null;
//        assert retrievedDatabaseInfo.getTables() != null;
//        assert retrievedDatabaseInfo.getTables().size() == 1;

//        TableInfo retrievedTableInfo = retrievedDatabaseInfo.getTables().get(0);
//        assert retrievedTableInfo.getColumns() != null;
//        assert retrievedTableInfo.getColumns().size() == 2;

        // You can perform other operations, such as updating and deleting, as needed
    }

    @Test
    public void testTableInfoOperations() {
        // Create a TableInfo object
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName("Test Table");
        tableInfo.setDescription("Test Description");

        // Create ColumnInfo objects
        ColumnInfo columnInfo1 = new ColumnInfo();
        columnInfo1.setColumnName("id");
        columnInfo1.setType("INT");
        columnInfo1.setDescription("ID Column");

        ColumnInfo columnInfo2 = new ColumnInfo();
        columnInfo2.setColumnName("name");
        columnInfo2.setType("VARCHAR");
        columnInfo2.setDescription("Name Column");

        List<ColumnInfo> columns = new ArrayList<>();
        columns.add(columnInfo1);
        columns.add(columnInfo2);

        tableInfo.setColumns(columns);

        // Save the TableInfo object and related data
        tableInfoRepository.save(tableInfo);
        System.out.println(tableInfo.getId());

        // Retrieve the saved TableInfo
        TableInfo retrievedTableInfo = tableInfoRepository.findById(tableInfo.getId());
        System.out.println("Retrieved TableInfo: " + retrievedTableInfo);

        // Verify the retrieved data
//        assert retrievedTableInfo != null;
//        assert retrievedTableInfo.getColumns() != null;
//        assert retrievedTableInfo.getColumns().size() == 2;

        // You can perform other operations, such as updating and deleting, as needed
    }

    @Test
    public void testColumnInfoOperations() {
        // Create a ColumnInfo object
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setColumnName("Test Column");
        columnInfo.setType("VARCHAR");
        columnInfo.setDescription("Test Description");

        // Save the ColumnInfo object
        columnInfoRepository.save(columnInfo);

        System.out.println(columnInfo.getId());

        // Retrieve the saved ColumnInfo
        ColumnInfo retrievedColumnInfo = columnInfoRepository.findById(columnInfo.getId());
        System.out.println("Retrieved ColumnInfo: " + retrievedColumnInfo);

        // Verify the retrieved data
//        assert retrievedColumnInfo != null;

        // You can perform other operations, such as updating and deleting, as needed
    }
}

