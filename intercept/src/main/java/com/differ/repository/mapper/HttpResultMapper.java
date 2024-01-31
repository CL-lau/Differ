package com.differ.repository.mapper;

import com.differ.dto.HttpResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
//@Repository
public interface HttpResultMapper {
    HttpResult selectHttpResultById(int id);
    boolean insertHttpResult(HttpResult httpResult);
    boolean updateHttpResult(HttpResult httpResult);
    int deleteHttpResultById(int id);
}
