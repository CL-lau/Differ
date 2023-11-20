package com.differ.compare.repository.db.mapper;

import com.differ.compare.entity.db.ColumnInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/3 0:20
 */

@Mapper
public interface ColumnInfoRepository {
    List<ColumnInfo> findAll();

    ColumnInfo findById(Long id);

    boolean save(ColumnInfo columnInfo);

    boolean update(ColumnInfo columnInfo);

    boolean delete(Long id);
}
