package org.taurus.common.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.taurus.common.load.properties.HtmlProperties;

@Configuration
public class HtmlConfig implements WebMvcConfigurer {
	
	@Resource
	private HtmlProperties htmlProperties;

	/**
	 * @Description: 对文件的路径进行配置, 创建一个虚拟路径/Path/**
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(htmlProperties.getVirtualPath()).addResourceLocations("file:" + htmlProperties.getRealPath());
	}
}
