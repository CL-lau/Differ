package com.differ.compare.service;

import com.differ.compare.entity.db.ColumnInfo;
import com.differ.compare.entity.db.DatabaseInfo;
import com.differ.compare.entity.db.TableInfo;

import java.util.List;

public interface DBChangeService {
    String getJsonResultByDB(DatabaseInfo databaseInfo);

    String getJsonResultByDB(List<DatabaseInfo> databaseInfos);

    String getJsonResultByTable(TableInfo tableInfo);

    String getJsonResultByTables(List<TableInfo> tableInfos);

    String getJsonResultByColumn(ColumnInfo columnInfo);

    String getJsonResultByColumns(List<ColumnInfo> columnInfos);
}
