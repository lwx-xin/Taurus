package org.taurus;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.taurus.common.util.JsonUtil;
import org.taurus.service.SMenuService;

@SpringBootTest
class TaurusApplicationTests {
	
	@Resource
	private SMenuService menuService;

	@Test
	void contextLoads() {
		System.err.println(JsonUtil.toJson(menuService.getMenuListByUser("1")));
	}
	

}
