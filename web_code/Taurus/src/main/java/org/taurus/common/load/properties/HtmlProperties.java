package org.taurus.common.load.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

@Order(1) // value越小越先加载
@PropertySource(value = { "classpath:html.properties" }, encoding = "utf-8")
@Configuration
public class HtmlProperties {

	/**
	 * 文件真实路径
	 */
	@Value("${html.real.path}")
	private String realPath;

	/**
	 * 文件虚拟路径
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
