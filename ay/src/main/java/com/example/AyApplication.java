package com.example;

import com.example.rmi.test.RMIExService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ImportResource(locations={"classpath:spring-mvc.xml"})
//filter和listener监听器注解需要用的的
@ServletComponentScan
@EnableRetry
@EnableAsync
@EnableCaching
public class AyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(AyApplication.class, args);
	}


	/* rmi 服务器暴漏 服务*/
	@Bean
	public RmiServiceExporter rmiServiceExporter(RMIExService rMIExService){
		RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
		//客户端通过rmi调用的端口
		rmiServiceExporter.setRegistryPort(6666);
		//客户端调用注册调用的服务名
		rmiServiceExporter.setServiceName("rmi");
		//注册的service
		rmiServiceExporter.setService(rMIExService);
		//注册的接口
		rmiServiceExporter.setServiceInterface(RMIExService.class) ;
		return rmiServiceExporter ;
	}

	/*本地注册远程服务，通过tcp 调用远程方法*/
	@Bean
	public RmiProxyFactoryBean rmiProxyFactoryBean() {
		RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
		rmiProxyFactoryBean.setServiceUrl("rmi://127.0.0.1:6666/rmi");
		rmiProxyFactoryBean.setServiceInterface(RMIExService.class);
		return rmiProxyFactoryBean;
	}

}
