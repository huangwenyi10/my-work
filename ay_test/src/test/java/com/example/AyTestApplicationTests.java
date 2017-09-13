package com.example;

import com.example.dubbo.test.UserDubboConsumerService;
import com.example.test.RMIExService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AyTestApplicationTests {


	@Autowired
	@Qualifier("rmiService")
	private RmiProxyFactoryBean factoryBean;


	@Test
	public void test() {
		RMIExService service = (RMIExService)factoryBean.getObject();
		System.out.println(service.invokingRemoteService());
		System.out.println(service.sayHello());
	}

	@Autowired
	private UserDubboConsumerService userDubboConsumerService;

	@Test
	public void dubboTest() {
		userDubboConsumerService.getUserByName("ay");
	}

}
