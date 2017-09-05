package com.example.rmi.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * Created by Ay on 2017/9/4.
 */
@Configuration
public class RMIConfig {

    @Autowired
    private RMIExServiceImpl rMIExService;
    /**
     * 方法描述：
     * 注意事项：
     * @return
     * @Exception 异常对象
     */
    @Bean
    public RmiServiceExporter initRmiServiceExporter(){
        System.out.println("---------------------------------------");
        RmiServiceExporter exporter=new RmiServiceExporter();
        exporter.setServiceInterface(RMIExService.class);
        exporter.setServiceName("jmxrmi");
        exporter.setService(rMIExService);
        exporter.setServicePort(1099);
        return exporter;
    }
}
