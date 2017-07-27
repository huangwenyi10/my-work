package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void contextLoads() {
		List<Map<String,Object>> result = jdbcTemplate.queryForList("select * from ay_test");
		System.out.println("query result is" + result.size());
		System.out.println("success");
	}

}
