package com.example.demo.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * Created by Ay on 2017/9/5.
 */
@Configuration
public class RMIClientConfig {
    @Bean(name="rmiService")
    public RmiProxyFactoryBean initRmiProxyFactoryBean() {
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://127.0.0.1:1099/jmxrmi");
        factoryBean.setServiceInterface(RMIExService.class);
//        factoryBean.setLookupStubOnStartup(false );
//        factoryBean.setRefreshStubOnConnectFailure(true );
//        factoryBean.afterPropertiesSet();
        return factoryBean;
    }
}