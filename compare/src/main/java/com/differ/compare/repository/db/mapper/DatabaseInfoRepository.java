package com.differ.compare.repository.db.mapper;

import com.differ.compare.entity.db.DatabaseInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/3 0:20
 */

@Mapper
public interface DatabaseInfoRepository {
    List<DatabaseInfo> findAll();

    DatabaseInfo findById(Long id);

    boolean save(DatabaseInfo databaseInfo);

    boolean update(DatabaseInfo databaseInfo);

    void delete(Long id);
}
