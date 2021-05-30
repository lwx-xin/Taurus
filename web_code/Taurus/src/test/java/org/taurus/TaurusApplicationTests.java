package org.taurus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.taurus.common.LogManager;
import org.taurus.config.load.data.InitAdminUserData;
import org.taurus.config.load.properties.TaurusProperties;

@SpringBootTest
class TaurusApplicationTests {
	
	@Autowired
	private LogManager service;

	@Autowired
	private TaurusProperties taurusProperties;

	@Autowired
	private InitAdminUserData initAdminUserData;

	@Test
	void initData(){
		initAdminUserData.initData();
	}

	@Test
	void contextLoads() {
	}
}
