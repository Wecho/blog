package com.wecho.core.spring.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tk.mybatis.mapper.common.Mapper;

import java.beans.PropertyVetoException;

@MapperScan(markerInterface= Mapper.class,basePackages ={"com.wecho.core.dao"},sqlSessionFactoryRef = "sqlSessionFactory")
public class JdbcConfig {
    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://106.14.138.164:3306/blog?useUnicode=true&characterEncoding=utf-8");//防止乱码
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setInitialPoolSize(5);
        dataSource.setMaxAdministrativeTaskTime(10);
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/com/wecho/core/sqlmap/*Mapper.xml"));
        return sessionFactory;
    }
}