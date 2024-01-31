package com.differ.config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

//@Configuration
public class DataSourceConfig {
//    @Bean
    public DriverManagerDataSource dataSourceConfig(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/differ");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }
}
