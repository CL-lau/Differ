package com.differ.repository.mapper;

import com.differ.entity.service.http.HttpServiceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HttpServiceEntityMapper {
    HttpServiceEntity selectHttpServiceEntityById(long id);
    List<HttpServiceEntity> selectAllHttpServiceEntities();
    boolean insertHttpServiceEntity(HttpServiceEntity httpServiceEntity);
    boolean updateHttpServiceEntity(HttpServiceEntity httpServiceEntity);
    boolean deleteHttpServiceEntityById(long id);
}