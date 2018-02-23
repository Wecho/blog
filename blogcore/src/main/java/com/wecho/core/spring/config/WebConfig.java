package com.wecho.core.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.wecho.core.api","com.wecho.core.controller"})
public class WebConfig extends WebMvcConfigurerAdapter{

    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver internalResourceViewResolver =
                new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/view/");
        internalResourceViewResolver.setSuffix(".jsp");
        internalResourceViewResolver.setExposeContextBeansAsAttributes(true);
        return internalResourceViewResolver;
    }
/*

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
        registry.addResourceHandler("/resources/js/**").addResourceLocations("/WEB-INF/resources/js/");
        registry.addResourceHandler("/resources/css/**").addResourceLocations("/WEB-INF/resources/css/");
        registry.addResourceHandler("/resources/img/**").addResourceLocations("/WEB-INF/resources/img/");
        registry.addResourceHandler("/resources/images/**").addResourceLocations("/WEB-INF/resources/images/");
        registry.addResourceHandler("/resources/fonts/**").addResourceLocations("/WEB-INF/resources/fonts/");
        registry.addResourceHandler("/resources/plugins/**").addResourceLocations("/WEB-INF/resources/plugins/");
    }
}
