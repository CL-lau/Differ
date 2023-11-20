package com.differ.compare;

import com.differ.compare.repository.influxdb.InfluxDBRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/10 22:14
 */

@SpringBootTest
public class InfluxDBTest {

    @Autowired
    private InfluxDBRepository influxDBRepository;

    @Test
    public void testInfluxDB() {
        String bucket = "differ";
        String org = "test";
        String measurementName = "testMeasurement";
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> fields = new HashMap<>();
            fields.put("mem", i*10);
            fields.put("io", i*11);
            fields.put("use", i*9);
            fields.put("name", "liu");
            this.influxDBRepository.insert(bucket, org, measurementName, fields, time-(i*60000));
        }
    }
}
