package com.differ.compare.service.impl;

import com.differ.compare.entity.ChangeDto;
import com.differ.compare.entity.db.ColumnInfo;
import com.differ.compare.entity.db.DatabaseInfo;
import com.differ.compare.entity.db.TableInfo;
import com.differ.compare.entity.enumer.ServiceType;
import com.differ.compare.repository.db.mapper.ChangeDtoRepository;
import com.differ.compare.service.DBChangeService;
import com.differ.compare.utils.FastjsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/7 20:35
 */
@Service
public class DBChangeServiceImpl implements DBChangeService {
    /**
     * 方法列表：
     * 1. 根据database，table，column来查询历史数据。List<Object>
     * 2. 根据database，table，column以及ts和es范围来查询历史数据。List<Object>
     * 3. 根据database，table来查询历史上数据。Map<TableName.ColumnName, List<Object>>
     * 4. 根据database，table以及ts和es范围来查询历史上数据。Map<TableName.ColumnName, List<Object>>
     * 5. 根据database范围来查询历史数据。返回类型:Map<DatabaseName.TableName.ColumnName, List<Object>>
     * 6. 根据database以及ts和es范围来查询历史数据。 返回类型:Map<DatabaseName.TableName.ColumnName, List<Object>>
     * 7. 根据List<database，table，column>来查询历史数据。Map<ServiceType, List<Object>>
     * 8. 根据List<database，table，column>以及ts和es范围来查询历史数据。Map<ServiceType, List<Object>>
     * 9. 根据List<database，table>来查询历史上数据。Map<ServiceType, Map<TableName.ColumnName, List<Object>>>
     * 10. 根据List<database，table>以及ts和es范围来查询历史上数据。Map<ServiceType, Map<TableName.ColumnName, List<Object>>>
     * 11. 根据List<database>范围来查询历史数据。返回类型:Map<ServiceType, Map<DatabaseName.TableName.ColumnName, List<Object>>>
     * 12. 根据List<database>以及ts和es范围来查询历史数据。
     *              返回类型:Map<ServiceType, Map<DatabaseName.TableName.ColumnName, List<Object>>>
     * 13. 根据requestUrl来查询HttpResult。返回类型：List<HttpResult>
     * 13. 根据requestUrl以及开始时间和结束时间来查询HttpResult。返回类型：List<HttpResult>
     * 13. 根据List<requestUrl>来查询HttpResult。返回类型：Map<request, List<HttpResult>>
     * 13. 根据List<requestUrl>以及开始时间和结束时间来查询HttpResult。返回类型：Map<request, List<HttpResult>>
     *
     */

    private ChangeDtoRepository changeDtoMapper;

    @Autowired
    public void setInject(ChangeDtoRepository changeDtoMapper){
        this.changeDtoMapper = changeDtoMapper;
    }

    @Override
    public String getJsonResultByDB(DatabaseInfo databaseInfo){
        return transResultMapToJsonV1(getColumnChangesByDB(databaseInfo));
    }

    @Override
    public String getJsonResultByDB(List<DatabaseInfo> databaseInfos){
        return transResultMapToJsonV1(getColumnChangesByDBs(databaseInfos));
    }

    @Override
    public String getJsonResultByTable(TableInfo tableInfo){
        return transResultMapToJsonV1(getColumnChangesByTable(tableInfo));
    }

    @Override
    public String getJsonResultByTables(List<TableInfo> tableInfos){
        return transResultMapToJsonV1(getColumnChangesByTables(tableInfos));
    }

    @Override
    public String getJsonResultByColumn(ColumnInfo columnInfo){
        return transResultMapToJsonV1(getColumnChangesByColumn(columnInfo));
    }

    @Override
    public String getJsonResultByColumns(List<ColumnInfo> columnInfos){
        return transResultMapToJsonV1(getColumnChangesByColumns(columnInfos));
    }

    public Map<ServiceType, Map<String, List<Object>>> getColumnChangesByDB(DatabaseInfo databaseInfo){
        List<ChangeDto> changeDtoList = this.getChangesByDB(databaseInfo);
        Map<ServiceType, Map<String, List<Object>>> resultMap = new HashMap<>();

        for (ChangeDto changeDto : changeDtoList) {
            ServiceType serviceType = changeDto.getServiceType();
            String key = getKey_DB_Table_Column(changeDto);

            resultMap
                    .computeIfAbsent(serviceType, k -> new HashMap<>())
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(changeDto.getNovelData());
        }
        return resultMap;
    }

    public Map<ServiceType, Map<String, List<Object>>> getColumnChangesByDBs(List<DatabaseInfo> databaseInfos){
        List<ChangeDto> changeDtoList = this.getChangesByDBs(databaseInfos);
        Map<ServiceType, Map<String, List<Object>>> resultMap = new HashMap<>();

        for (ChangeDto changeDto : changeDtoList) {
            ServiceType serviceType = changeDto.getServiceType();
            String key = getKey_DB_Table_Column(changeDto);

            resultMap
                    .computeIfAbsent(serviceType, k -> new HashMap<>())
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(changeDto.getNovelData());
        }
        return resultMap;
    }

    public List<ChangeDto> getChangesByDB(DatabaseInfo databaseInfo) {
        List<ChangeDto> changeDtoList = this.changeDtoMapper.findByDatabaseName(databaseInfo.getDatabaseName());
        return changeDtoList;
    }

    public List<ChangeDto> getChangesByDBs(List<DatabaseInfo> databaseInfos) {
        List<ChangeDto> changes = new ArrayList<>();
        databaseInfos.forEach(
                databaseInfo -> {
                    changes.addAll(this.getChangesByDB(databaseInfo));
                }
        );
        return changes;
    }

    public Map<ServiceType, Map<String, List<Object>>> getColumnChangesByTable(TableInfo tableInfo){
        List<ChangeDto> changeDtoList = this.getChangesByTable(tableInfo);
        Map<ServiceType, Map<String, List<Object>>> resultMap = new HashMap<>();

        for (ChangeDto changeDto : changeDtoList) {
            ServiceType serviceType = changeDto.getServiceType();
            String key = getKey_DB_Table_Column(changeDto);

            resultMap
                    .computeIfAbsent(serviceType, k -> new HashMap<>())
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(changeDto.getNovelData());
        }
        return resultMap;
    }

    public Map<ServiceType, Map<String, List<Object>>> getColumnChangesByTables(List<TableInfo> tableInfos){
        List<ChangeDto> changeDtoList = this.getChangesByTables(tableInfos);
        Map<ServiceType, Map<String, List<Object>>> resultMap = new HashMap<>();

        for (ChangeDto changeDto : changeDtoList) {
            ServiceType serviceType = changeDto.getServiceType();
            String key = getKey_DB_Table_Column(changeDto);

            resultMap
                    .computeIfAbsent(serviceType, k -> new HashMap<>())
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(changeDto.getNovelData());
        }
        return resultMap;
    }

    public List<ChangeDto> getChangesByTable(TableInfo tableInfo){
        Map<String, String> params = new HashMap<>();
        params.put("databaseName", tableInfo.getDatabaseName());
        params.put("tableName", tableInfo.getTableName());
        List<ChangeDto> changes = this.changeDtoMapper.findByDatabaseNameAndTableName(params);
        return changes;
    }

    public List<ChangeDto> getChangesByTables(List<TableInfo> tableInfos){
        List<ChangeDto> changes = new ArrayList<>();
        tableInfos.forEach(
                tableInfo -> {
                    Map<String, String> params = new HashMap<>();
                    params.put("databaseName", tableInfo.getDatabaseName());
                    params.put("tableName", tableInfo.getTableName());
                    changes.addAll(this.changeDtoMapper.findByDatabaseNameAndTableName(params));
                }
        );
        return changes;
    }

    public Map<ServiceType, Map<String, List<Object>>> getColumnChangesByColumn(ColumnInfo columnInfo){
        List<ChangeDto> changeDtoList = this.getChangesByColumn(columnInfo);
        Map<ServiceType, Map<String, List<Object>>> resultMap = new HashMap<>();

        for (ChangeDto changeDto : changeDtoList) {
            ServiceType serviceType = changeDto.getServiceType();
            String key = getKey_DB_Table_Column(changeDto);

            resultMap
                    .computeIfAbsent(serviceType, k -> new HashMap<>())
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(changeDto.getNovelData());
        }
        return resultMap;
    }

    public Map<ServiceType, Map<String, List<Object>>> getColumnChangesByColumns(List<ColumnInfo> columnInfos){
        List<ChangeDto> changeDtoList = this.getChangesByColumns(columnInfos);
        Map<ServiceType, Map<String, List<Object>>> resultMap = new HashMap<>();

        for (ChangeDto changeDto : changeDtoList) {
            ServiceType serviceType = changeDto.getServiceType();
            String key = getKey_DB_Table_Column(changeDto);

            resultMap
                    .computeIfAbsent(serviceType, k -> new HashMap<>())
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(changeDto.getNovelData());
        }
        return resultMap;
    }

    public List<ChangeDto> getChangesByColumn(ColumnInfo columnInfo){
        Map<String, String> params = new HashMap<>();
        params.put("databaseName", columnInfo.getDatabaseName());
        params.put("tableName", columnInfo.getTableName());
        List<ChangeDto> changes = this.changeDtoMapper.findByDatabaseNameAndTableName(params);
        List<ChangeDto> filteredList = changes.stream()
                .filter(changeDto -> columnInfo.getColumnName().equalsIgnoreCase(changeDto.getColumnName()))
                .collect(Collectors.toList());
        return changes;
    }

    public List<ChangeDto> getChangesByColumns(List<ColumnInfo> columnInfos){
        List<ChangeDto> changes = new ArrayList<>();
        columnInfos.forEach(
                columnInfo -> {
                    Map<String, String> params = new HashMap<>();
                    params.put("databaseName", columnInfo.getDatabaseName());
                    params.put("tableName", columnInfo.getTableName());
                    List<ChangeDto> filteredList = this.changeDtoMapper.findByDatabaseNameAndTableName(params).stream()
                            .filter(changeDto -> columnInfo.getColumnName().equalsIgnoreCase(changeDto.getColumnName()))
                            .collect(Collectors.toList());
                    changes.addAll(filteredList);
                }
        );
        return changes;
    }

    private static String getKey_DB_Table_Column(ChangeDto changeDto) {
        return changeDto.getDatabaseName() + "_" + changeDto.getTableName() + "_" + changeDto.getColumnName();
    }

    private static String transResultMapToJsonV1(Map<ServiceType, Map<String, List<Object>>> values){
//        Map<String, Map<String, List<Object>>> tmp = new HashMap<>();
        Map<String, Map<String, List<List<Object>>>> tmp = new HashMap<>();
        values.forEach(
                (key, value) -> {
                    Map<String, List<List<Object>>> map = new HashMap<>();
                    value.forEach(
                            (keyString, valueList) -> {
                                List<List<Object>> list = new ArrayList<>();
                                valueList.forEach(
                                        v -> {
                                            list.add(new ArrayList<>(Collections.singletonList(v)));
                                        }
                                );
                                map.put(keyString, list);
                            }
                    );
//                    tmp.put(key.getType(), value);
                    tmp.put(key.getType(), map);
                }
        );
        return FastjsonUtils.transMap2JsonV3(tmp);
    }

    private static String transResultMapToJsonV2(Map<ServiceType, Map<String, List<Object>>> values){
        Map<String, Map<String, List<Object>>> tmp = new HashMap<>();
        values.forEach(
                (key, value) -> {
                    tmp.put(key.getType(), value);
                }
        );
        return FastjsonUtils.transMap2JsonV2(tmp);
    }
}
