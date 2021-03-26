package org.taurus.config.bean;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.taurus.config.filter.AuthFilter;

@Configuration
public class FilterConfig {
	
    @Bean
    public Filter authFilter(){
        return new AuthFilter();
    }
    
    @Bean
    public FilterRegistrationBean<Filter> setUrlFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(authFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(1);   //order的数值越小，在所有的filter中优先级越高
        filterRegistrationBean.setName("urlFilter");
        return filterRegistrationBean;
    }
}
