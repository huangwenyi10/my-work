package com.example.demo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * Created by Ay on 2017/9/4.
 */
public class RMIConfig {

    @Autowired
    @Qualifier("rMIExService")
    private RMIExServiceImpl serviceImpl;
    /**
     * 方法描述：
     * 注意事项：
     * @return
     * @Exception 异常对象
     */
    @Bean
    public RmiServiceExporter initRmiServiceExporter(){
        RmiServiceExporter exporter=new RmiServiceExporter();
        exporter.setServiceInterface(RMIExService.class);
        exporter.setServiceName("rmiService");
        exporter.setService(serviceImpl);
        exporter.setServicePort(6666);
        return exporter;
    }
}
