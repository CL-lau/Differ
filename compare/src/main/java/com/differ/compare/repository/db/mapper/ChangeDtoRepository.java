package com.differ.compare.repository.db.mapper;

import com.differ.compare.entity.ChangeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChangeDtoRepository {
    boolean insert(ChangeDto changeDto);

    boolean update(ChangeDto changeDto);

    boolean delete(Long id);

    ChangeDto findById(Long id);

    List<ChangeDto> findAll();

    List<ChangeDto> findInRangeByESAndTS(Map<String, Long> parameters);

    List<ChangeDto> findByDatabaseName(String databaseName);

    List<ChangeDto> findByDatabaseNameAndTableName(Map<String, String> parameters);

    List<ChangeDto> findBySqlType(String sqlType);

    List<ChangeDto> findByServiceType(int serviceType);

    List<ChangeDto> findByServiceTypeDatabaseNameAndTableName(Map<String, Object> parameters);

}

