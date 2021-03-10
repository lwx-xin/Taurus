package org.taurus.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.taurus.config.load.properties.PathProperties;
import org.taurus.config.load.properties.TaurusProperties;

@Configuration
public class HtmlConfig implements WebMvcConfigurer {

	@Resource
	private PathProperties pathProperties;
	
	@Resource
	private TaurusProperties taurusProperties;

	/**
	 * @Description: 对文件的路径进行配置, 创建一个虚拟路径/Path/**
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 访问前端HTML的虚拟路径
		registry.addResourceHandler(pathProperties.getVirtualPath()).addResourceLocations("file:" + pathProperties.getRealPath());
		// 在线访问文件的虚拟路径
		registry.addResourceHandler(taurusProperties.getFolderRootVirtual()).addResourceLocations("file:" + taurusProperties.getFolderRoot());
	}
}
