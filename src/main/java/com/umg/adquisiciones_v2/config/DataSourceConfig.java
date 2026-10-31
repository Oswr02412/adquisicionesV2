package com.umg.adquisiciones_v2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Value("${db.sqlserver.url}") private String sqlUrl;
    @Value("${db.sqlserver.username}") private String sqlUser;
    @Value("${db.sqlserver.password}") private String sqlPass;

    @Value("${db.postgresql.url}") private String pgUrl;
    @Value("${db.postgresql.username}") private String pgUser;
    @Value("${db.postgresql.password}") private String pgPass;

    @Bean
    public DataSource sqlServerDataSource() {
        return DataSourceBuilder.create().url(sqlUrl).username(sqlUser).password(sqlPass)
                .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver").build();
    }

    @Bean
    public DataSource postgreSqlDataSource() {
        return DataSourceBuilder.create().url(pgUrl).username(pgUser).password(pgPass)
                .driverClassName("org.postgresql.Driver").build();
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        
        targetDataSources.put("SQLServer", sqlServerDataSource());
        targetDataSources.put("PostgreSQL", postgreSqlDataSource());
        
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(sqlServerDataSource());
        return dynamicDataSource;
    }
}
