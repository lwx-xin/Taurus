package org.taurus.config.load.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

@Order(2) // value越小越先加载
@PropertySource(value = { "classpath:path.properties" }, encoding = "utf-8")
@Configuration
public class PathProperties {

	/**
	 * html文件真实路径
	 */
	@Value("${html.real.path}")
	private String realPath;
	/**
	 * html文件虚拟路径
	 */
	@Value("${html.virtual.path}")
	private String virtualPath;

	public String getRealPath() {
		return realPath;
	}

	public String getVirtualPath() {
		return virtualPath;
	}

}
