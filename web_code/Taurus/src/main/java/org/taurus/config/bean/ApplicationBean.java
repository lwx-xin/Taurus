package org.taurus.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.taurus.config.load.data.InitAdminUserData;

import javax.annotation.Resource;

@Configuration
public class ApplicationBean {

    @Resource
    private InitAdminUserData initAdminUserData;

    @Bean
    public void initAdminUser(){
        initAdminUserData.initData();
    }
}
