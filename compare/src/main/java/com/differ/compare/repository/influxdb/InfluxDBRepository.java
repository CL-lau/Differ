package com.differ.compare.repository.influxdb;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/10 17:50
 */

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
public class InfluxDBRepository{

//    @Qualifier("writeApi")
//    @Autowired
    private WriteApiBlocking writeApiBlocking;

    private InfluxDBClient influxDBClient;

    @Autowired
    public void setInject(@Qualifier("writeApi") WriteApiBlocking writeApiBlocking, @Qualifier("influxDBClient") InfluxDBClient influxDBClient){
        this.writeApiBlocking = writeApiBlocking;
        this.influxDBClient = influxDBClient;
    }

    public void insert(String bucket, String org, String measurementName, Map<String, Object> fields, Instant time){
        Point point = Point
                .measurement(measurementName)
//                .addTag("host", "host1")
                .addFields(fields)
                .time(time, WritePrecision.NS);

        this.writeApiBlocking.writePoint(bucket, org, point);
    }

    public void insert(String bucket, String org, String measurementName, Map<String, Object> fields, long time){
        Point point = Point
                .measurement(measurementName)
//                .addTag("host", "host1")
                .addFields(fields)
                .time(Instant.ofEpochMilli(time), WritePrecision.MS);

        this.writeApiBlocking.writePoint(bucket, org, point);
    }

    public void insert(String bucket, String org, String measurementName, Map<String, Object> fields, long time, Map<String, String> tags){
        Point point = Point
                .measurement(measurementName)
                .addTags(tags)
                .addFields(fields)
                .time(Instant.ofEpochMilli(time), WritePrecision.MS);

        this.writeApiBlocking.writePoint(bucket, org, point);
    }

    public void insert(String bucket, String org, Point point){
        this.writeApiBlocking.writePoint(bucket, org, point);
    }

    public List<FluxTable> query(String bucketName, String measurementName, long startTime, long endTime){
        String queryString = "from(bucket: \"" + bucketName + "\")\n" +
                "  |> range(start: " + startTime + ", stop: " + endTime + ")\n" +
                "  |> filter(fn: (r) => r._measurement == \"" + measurementName + "\")";
        List<FluxTable> result = this.influxDBClient.getQueryApi().query(queryString);
        return result.isEmpty() ? null : result;
    }
}
