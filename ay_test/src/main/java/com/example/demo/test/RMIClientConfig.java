package com.example.demo.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * Created by Ay on 2017/9/5.
 */
@Configuration
public class RMIClientConfig {
    @Bean
    public RmiProxyFactoryBean initRmiProxyFactoryBean() {
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://localhost:8080/rmiService");
        factoryBean.setServiceInterface(RMIExService.class);
        factoryBean.setLookupStubOnStartup(false );
        factoryBean.setRefreshStubOnConnectFailure(true );
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }
}