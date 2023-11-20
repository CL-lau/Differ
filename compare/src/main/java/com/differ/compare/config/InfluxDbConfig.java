package com.differ.compare.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import org.influxdb.InfluxDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/10 17:34
 */
@Configuration
public class InfluxDbConfig {
    @Value("${influxdb.url}")
    private String influxDbUrl;

    @Value("${influxdb.token}")
    private String influxDbToken;

    @Value("${influxdb.org}")
    private String influxDbOrg;

    @Bean
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(influxDbUrl, influxDbToken.toCharArray(), influxDbOrg);
    }

//    @Bean
    public InfluxDB influxDB() {
        return null;
    }

//    @Bean
//    public InfluxDBClient influxDBClient() {
//        return InfluxDBClientFactory.create(influxDbUrl, influxDbToken.toCharArray());
//    }

    @Bean
    public WriteApiBlocking writeApi() {
        InfluxDBClient client = InfluxDBClientFactory.create(influxDbUrl, influxDbToken.toCharArray(), influxDbOrg);
        return client.getWriteApiBlocking();
    }

    @Bean
    public WriteApiBlocking writeApiWithClient(InfluxDBClient client) {
        return client.getWriteApiBlocking();
    }
}
