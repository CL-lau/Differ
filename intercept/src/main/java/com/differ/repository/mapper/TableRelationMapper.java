package com.differ.repository.mapper;

import com.differ.dto.TableRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TableRelationMapper {
    TableRelation selectTableRelationById(int id);
    int insertTableRelation(TableRelation tableRelation);
    int updateTableRelation(TableRelation tableRelation);
    int deleteTableRelationById(int id);
}