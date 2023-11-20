package com.differ.compare.service.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/10 17:44
 */
@Component
public class InfluxDbInitializer {

//    @Autowired
    private InfluxDBClient influxDBClient;

    @Autowired
    public void setInjections(InfluxDBClient influxDBClient){
        this.influxDBClient = influxDBClient;
    }

    private void createBucketIfNeeded(String bucketName) {
        QueryApi queryApi = influxDBClient.getQueryApi();
        String query = String.format("CREATE BUCKET IF NOT EXISTS \"%s\" WITH (retention_policy=\"%s\")", bucketName, "30d");
        queryApi.query(query);
    }

    public void createMeasurement(String bucketName, String org, String measurementName, List<Map.Entry<String, Object>> fields) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        Point point = Point.measurement(measurementName)
                .time(Instant.now(), WritePrecision.NS);

        writeApi.writePoint(bucketName, org, point);

    }
}
