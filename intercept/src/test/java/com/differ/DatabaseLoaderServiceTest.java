package com.differ;

import com.differ.compare.service.db.DatabaseLoaderService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @description:
 * @author: lau
 * @time: 2023/11/3 19:19
 */
@SpringBootTest
public class DatabaseLoaderServiceTest {

    @Resource
    private DatabaseLoaderService databaseLoaderService;

    @Test
    public void testLoadDatabaseInfo() {
        databaseLoaderService.loadDatabaseInfo();
    }
}
