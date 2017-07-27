package com.example.demo;

import com.example.mybatis.test.AppMessage;
import com.example.mybatis.test.AppMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private AppMessageService appMessageService;

	@Test
	public void contextLoads() {
		List<Map<String,Object>> result = jdbcTemplate.queryForList("select * from ay_test");
		System.out.println("query result is" + result.size());
		System.out.println("success");
	}

	@Test
	public void myBatisTest(){
		List<AppMessage> lists = appMessageService.getAllMessage();
		System.out.println("success");
	}

}
