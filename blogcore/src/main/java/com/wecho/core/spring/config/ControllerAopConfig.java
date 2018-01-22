package com.wecho.core.spring.config;

import com.wecho.core.aop.ControllerLog;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.wecho.core.aop")
public class ControllerAopConfig {

    @Bean
    public ControllerLog controllerLog(){
        return new ControllerLog();
    }
}
