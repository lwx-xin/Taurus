package org.taurus;


import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.taurus.common.LogManager;

@SpringBootTest
class TaurusApplicationTests {
	
	@Autowired
	private LogManager service;

	@Test
	void contextLoads() {
		
		String adminId = "00000000-0000-0000-0000-000000000000";
	}
	

}
