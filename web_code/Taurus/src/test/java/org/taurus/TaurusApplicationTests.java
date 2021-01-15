package org.taurus;


import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.taurus.common.LogManager;

@SpringBootTest
class TaurusApplicationTests {
	
	@Resource
	private LogManager service;

	@Test
	void contextLoads() {
	}
	

}
