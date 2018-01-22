package com.wecho.core.spring.config;

import com.wecho.core.model.Domain;
import com.wecho.core.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Domain.class)
public class LoginConfig {

    @Bean
    public User getUser(){
        return new User();
    }


}