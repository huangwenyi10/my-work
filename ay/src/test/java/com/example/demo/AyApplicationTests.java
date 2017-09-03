package com.example.demo;

import com.example.annotation.test.AnnotationTest;
import com.example.mq.test.ConsumerService;
import com.example.mq.test.ProducerService;
import com.example.rmi.test.RMIExService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AyApplicationTests {

	@Autowired
	@Resource
	private ProducerService producerService;
	@Autowired
	private ConsumerService consumerService;
	@Autowired
	private Destination queueDestination;

	@Autowired
	private AnnotationTest annotationTest;

	@Value("${face_api_key}")
	private String API_KEY;
	@Value("${face_api_secret}")
	private String API_SECRET;

	@Test
	public void myMQProductorTest(){
		producerService.sendMessage("hello ay....");
	}

	@Test
	public void myMQComsumerTest(){
		consumerService.receive(queueDestination);
	}

	@Test
	public void annotationTest(){
		System.out.println(annotationTest.getId());
		System.out.println(annotationTest.getName());
		System.out.println(API_KEY);
	}

	@Autowired
	private RMIExService rmiExService;

	@Test
	public void rmiTest(){
		rmiExService.invokingRemoteService();
	}

}
