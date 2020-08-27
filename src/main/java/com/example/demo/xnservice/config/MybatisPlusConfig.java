package com.example.demo.xnservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
@Configuration
@MapperScan("com.example.demo.mapper")
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        // 设置方言
        page.setDialectType("mysql");
        return page;
    }


}
