package com.differ.compare.repository.db.mapper;

import com.differ.compare.entity.db.TableInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/3 0:20
 */
@Mapper
public interface TableInfoRepository {
    List<TableInfo> findAll();

    TableInfo findById(Long id);

    boolean save(TableInfo tableInfo);

    boolean update(TableInfo tableInfo);

    boolean delete(Long id);
}
