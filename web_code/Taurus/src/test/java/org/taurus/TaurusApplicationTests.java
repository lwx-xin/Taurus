package org.taurus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.taurus.common.util.ListUtil;
import org.taurus.service.SMenuService;

@SpringBootTest
class TaurusApplicationTests {
	
	@Resource
	private SMenuService menuService;

	@Test
	void contextLoads() {
		List<String> a = new ArrayList<>(Arrays.asList("aaa","bbb","ccc","ddd"));
		List<String> b = new ArrayList<>(Arrays.asList("aaa","123","ccc","fff"));
		
		System.err.println(ListUtil.except(a, b));
		System.err.println(a);
		System.err.println(b);
	}
	

}
