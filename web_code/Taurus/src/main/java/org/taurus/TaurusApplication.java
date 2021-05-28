package org.taurus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan(value = "org.taurus.dao")
public class TaurusApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder){
		return springApplicationBuilder.sources(TaurusApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(TaurusApplication.class, args);
	}

}
