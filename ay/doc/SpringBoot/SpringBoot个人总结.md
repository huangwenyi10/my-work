# Spring Boot个人总结



### **快速搭建Spring Boot项目**



【1】http://blog.csdn.net/huangwenyi1010/article/details/55047138



### **Spring Boot 集成mySQL**



##### **用JdbcTemplate**



参考文章：http://www.cnblogs.com/liangblog/p/5228548.html

1） 添加依赖

```
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```



2）添加配置文件配置数据库和其他参数

在resource文件夹下添加application.properties配置文件并输入数据库参数，如下：

```
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/test
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.max-idle=10
spring.datasource.max-wait=10000
spring.datasource.min-idle=5
spring.datasource.initial-size=5

server.port=8011
server.session.timeout=10
server.tomcat.uri-encoding=UTF-8
```



3）新建测试类连接数据库

```
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

```





### **Spring Boot集成MyBatis**



MyBatis的集成总共有3中方式，这里讲解最简单的一种方式。



**第1步：引入mybatis的starter的包。** Spring Boot将封装的一系列支持boot的应用的工程都叫做starter，我们这里引入mybatis对boot的支持的starter。如果是同一个的pom，要注释掉mybatis的依赖，starter会自动引入依赖包。

**pom.xml**

```
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>12345
```

**第2步：配置properties。**

```
spring.datasource.driver-class-name=com.mysql.jdbc.Driver //1
spring.datasource.url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=test
spring.datasource.password=123456
spring.datasource.max-active=10
spring.datasource.max-idle=5
spring.datasource.min-idle=0

mybatis.mapper-locations=classpath:/mybatis/*Mapper.xml //2
mybatis.type-aliases-package=com.hjf.boot.demo.boot_mybatis.domain
```



**第3步：生成接口AppMessageMapper文件**



```
package com.example.mybatis.test;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
public interface AppMessageMapper {

    int deleteByPrimaryKey(String id);

    int insert(AppMessage record);

    int insertSelective(AppMessage record);

    AppMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AppMessage record);

    int updateByPrimaryKey(AppMessage record);

    List<AppMessage> selectAll();

    List<AppMessage> getMessById(String id);
}
```



**第四步：生成实体类，服务层，服务层实现层，控制层**



```
package com.example.mybatis.test;

import java.util.Date;
public class AppMessage {

    private String id;

    private String message;

    private Date senddate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getSenddate() {
        return senddate;
    }

    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }
}
```



```
package com.example.mybatis.test;

import java.util.ArrayList;
import java.util.List;

public interface IAppMessageService {

    public List<AppMessage> getMessage();
    public List<AppMessage> getAllMessage();
    public int addMessage(AppMessage appMessage);
    public List<AppMessage> getMessageById(String id);
    public int delMessage(String id);
}
```



```
package com.example.mybatis.test;

import java.util.ArrayList;
import java.util.List;
import com.example.mybatis.test.AppMessage;
import com.example.mybatis.test.AppMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Component
public class AppMessageService implements IAppMessageService {

    @Autowired
    private AppMessageMapper appMessageMapper;

    @Override
    public List<AppMessage> getMessage(){
        List<AppMessage> list = new ArrayList<>();
        list.add(appMessageMapper.selectByPrimaryKey("xtt"));
        //list = mapper.selectAll();
        return list;
    }

    @Override
    public List<AppMessage> getAllMessage(){
        List<AppMessage> list = new ArrayList<>();
        list = appMessageMapper.selectAll();
        return list;
    }

    @Override
    public int addMessage(AppMessage appMessage) {
        return appMessageMapper.insert(appMessage);
    }

    @Override
    public List<AppMessage> getMessageById(String id) {
        return appMessageMapper.getMessById(id);
    }

    @Override
    public int delMessage(String id) {
        return appMessageMapper.deleteByPrimaryKey(id);
    }



}
```



```
package com.example.mybatis.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appmessage")
public class APPMessageController {


    private AppMessageService appMessageService;

    @RequestMapping("/getThree")
    public List<AppMessage> getThreeForMessage(){
        List<AppMessage> list = appMessageService.getMessage();
        return list;
    }

    @RequestMapping("/getAll")
    public List<AppMessage> getAllMessage(){
        List<AppMessage> list = appMessageService.getAllMessage();
        int num = list.size();
        if(null!=list && num>3){
            for (int i = 0; i < num-3; i++) {
                list.remove(0);
            }
        }
        return list;
    }

    @RequestMapping("/getByID")
    public List<AppMessage> getMessageById(@RequestParam("id") String id){
        List<AppMessage> list = appMessageService.getMessageById(id);
        int num = list.size();
        if(null!=list && num>5){
            for (int i = 0; i < num-5; i++) {
                list.remove(0);
            }
        }
        return list;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public int addMessage(@RequestBody AppMessage appMessage){
        return appMessageService.addMessage(appMessage);
    }

    @RequestMapping(value="/delMessageById",method=RequestMethod.POST)
    public int delMessageById(@RequestParam("id") String id){
        return appMessageService.delMessage(id);
    }
}
```



**第五步：创建数据库表**（mysql数据库）

```
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `appuser_message`
-- ----------------------------
DROP TABLE IF EXISTS `appuser_message`;
CREATE TABLE `appuser_message` (
  `id` varchar(32) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  `senddate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
-- ----------------------------
-- Records of appuser_message
-- ----------------------------
INSERT INTO `appuser_message` VALUES ('1', '1', '2017-07-28');

```



文件的目录图如下：



![QQ截图20170727111913](C:\Users\huangwy\Desktop\md-image\QQ截图20170727111913.png)



##### **参考文章**

【1】http://m.blog.csdn.net/mickjoust/article/details/51646658



### **Spring Boot标签总结**



##### @SpringBootApplication

```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartApp {
    public static void main(String[] args) {
        SpringApplication.run(StartApp.class,args);
    }
}
```

**说明：** 
1：用这个注解，就能实现自动扫描包和自动配置默认配置的功能，它**包含了@ComponentScan和@EnableAutoConfiguration这两个注解**，**同时这个类自身也是一个配置类@Configuration，**可以直接在这个类里添加@Bean来注入java bean，第一章用的注解组合实现的和这个注解功能是一致的，这也是Spring Boot官方推荐的配置方式。



##### **@configuration**



用@Configuration注解该类，等价于XML中配置beans；用@Bean标注方法等价于XML中配置bean。

代码如下：



```
package SpringStudy;  

import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import SpringStudy.Model.Counter;  
import SpringStudy.Model.Piano;  

@Configuration  
public class SpringConfig {  
   @Bean  
   public Piano piano(){  
        return new Piano(); 
    }  

   @Bean(name = "counter")   
   public Counter counter(){  
   		return  new Counter(12,"Shake it Off",piano());  
   }  
} 
```



##### **@ImportResource**



类级别注解，当我们必须使用一个xml的配置时，使用@ImportResource和@Configuration来标识这个文件资源的类。 



编写ConfigClass注入配置文件application-bean.xml;

在com.kfit.config包下编写类ConfigClass，这个确保能被Spring Boot可以扫描到，不然一切都付之东流了，具体代码如下：

com.kfit.config].ConfigClass：



```
package com.kfit.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
/**
 * classpath路径：locations={"classpath:application-bean1.xml","classpath:application-bean2.xml"}
 * file路径： locations = {"file:d:/test/application-bean1.xml"};
 */
@Configuration
@ImportResource(locations={"classpath:application-bean.xml"})
//@ImportResource(locations={"file:d:/test/application-bean1.xml"})
public class ConfigClass {

}
 

```



##### **@RestController**



##### **@RequestMapping**







##### **@value**

通过@value注解来读取application.properties里面的配置

```
face++ key
face_api_key = R9Z3Vxc7ZcxfewgVrjOyrvu1d-qR  
face_api_secret =D9WUQGCYLvOCIdsbX35uTH****  
```

```
@Value("${face_api_key}")  
private String API_KEY;  
@Value("${face_api_secret}")  
private String API_SECRET;  
```

注意使用這個注解的时候 使用@Value的类如果被其他类作为对象引用，必须要使用注入的方式，而不能new。这个很重要，我就是被这个坑了。



### **Spring Boot 整合Quartz**



**第一步：添加依赖**



```
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
    <version>2.2.3</version>
</dependency>
```



第二步：定义配置文件 quartz.xml，名字自己取就可以了



```
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:task="http://www.springframework.org/schema/task"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-4.2.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task.xsd">

    <aop:config proxy-target-class="true"/>
    <context:annotation-config/>
	<!-- 利用import引入定时器的文件 -->
    <import resource="spring-quartz.xml"/>

</beans>
```



```
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">

    <!-- 使用MethodInvokingJobDetailFactoryBean，任务类可以不实现Job接口，通过targetMethod指定调用方法-->
    <bean id="taskJob" class="com.example.quartz.test.TestTask"/>
    <bean id="jobDetail" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
        <property name="group" value="job_work"/>
        <property name="name" value="job_work_name"/>
        <!--false表示等上一个任务执行完后再开启新的任务-->
        <property name="concurrent" value="false"/>
        <property name="targetObject">
            <ref bean="taskJob"/>
        </property>
        <property name="targetMethod">
            <value>run</value>
        </property>
    </bean>
    <!--  调度触发器 -->
    <bean id="myTrigger" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
        <property name="name" value="work_default_name"/>
        <property name="group" value="work_default"/>
        <property name="jobDetail">
            <ref bean="jobDetail" />
        </property>
        <property name="cronExpression">
            <value>0/10 * * * * ?</value>
        </property>
    </bean>
    <!-- 调度工厂 -->
    <bean id="scheduler" class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
        <property name="triggers">
            <list>
                <ref bean="myTrigger"/>
            </list>
        </property>
    </bean>
</beans>
```



第三步：创建定时器需要的类 TestTask



```
package com.example.quartz.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTask {

    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(TestTask.class);
    public void run() {
        if (LOG.isInfoEnabled()) {
            LOG.info("测试任务线程开始执行");
        }
        System.out.println("quarty run .....");
    }
}
```



**第四步：让spring boot扫描到配置文件**

主要是这个注解 @ImportResource(locations={"classpath:spring-mvc.xml"})，这样spring boot就可以扫描到我们的配置文件了

```
package com.example;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ImportResource(locations={"classpath:spring-mvc.xml"})
@EnableScheduling
public class DemoApplication{

   @Autowired
   private JmsTemplate jmsTemplate;

   public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
   }
}
```



### **多个配置文件的整合**

例如，项目中有多个xml配置文件：

- Spring-Common.xml位于common文件夹下
- Spring-Connection.xml位于connection文件夹下
- Spring-ModuleA.xml位于moduleA文件夹下

 你可以在代码中加载以上3个xml配置文件

```
ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Spring-Common.xml","Spring-Connection.xml","Spring-ModuleA.xml"});
```



但是这种方法不易组织并且不好维护，最好的方法是在一个单独的xml的配置文件中组织其他所有的xml配置文件。例如，可以创建一个Spring-All-Module.xml文件，然后将其他的xml配置文件导入到Spring-All-Module.xml中，就像下边这样，

 

Spring-All-Module.xml

```
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">

    <import resource="common/Spring-Common.xml"/>
    <import resource="connection/Spring-Connection.xml"/>
    <import resource="moduleA/Spring-ModuleA.xml"/>
</beans>
```

现在，你可以在代码中加载一个单独的xml配置文件，如下：

```
ApplicationContext context =    new ClassPathXmlApplicationContext(Spring-All-Module.xml);
```

 



### **Spring Boot整合Redis**



**第一步：添加依赖**

```
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-redis -->
<!-- 注意包都要拿最新的版本 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-redis</artifactId>
	<version>1.4.7.RELEASE</version>
</dependency>
```

**第二步：配置properties**



```
#redis
spring.redis.database=0
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=
spring.redis.pool.max-active=8
spring.redis.pool.max-wait=-1
spring.redis.pool.max-idle=8
spring.redis.pool.min-idle=0
spring.redis.timeout=0
```



##### **第三步：测试类**

```
package com.example.redis.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Ay
 * @date   2017/1/24.
 */
@RestController
@EnableAutoConfiguration
public class HelloRedisTestController {

    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/redis")
    public String index(){
        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");
        String string = stringRedisTemplate.opsForValue().get("aaa");
        System.out.println(string);
        return "Hello Ay...";
    }
}
```





##### **参考文章**

【1】http://blog.csdn.net/helenyqa/article/details/60575885



### **Spring Boot 整合 ActiveMQ**



##### **ActiveMQ安装**

【1】



第一步：添加依赖



```
<!-- ActiveMQ dependency start-->
<dependency>
   <groupId>org.springframework</groupId>
   <artifactId>spring-jms</artifactId>
</dependency>
<dependency>
   <groupId>org.apache.activemq</groupId>
   <artifactId>activemq-client</artifactId>
</dependency>
<!-- ActiveMQ dependency end-->
```



第二步：定义配置文件



```
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:task="http://www.springframework.org/schema/task"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-4.2.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task.xsd">

    <aop:config proxy-target-class="true"/>
    <context:annotation-config/>

    <import resource="spring-quartz.xml"/>


    <!-- 配置JMS连接工厂 -->
    <bean id="connectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
        <property name="brokerURL" value="failover:(tcp://localhost:61616)" />
    </bean>

    <!-- 定义消息队列（Queue） -->
    <bean id="queueDestination" class="org.apache.activemq.command.ActiveMQQueue">
        <!-- 设置消息队列的名字 -->
        <constructor-arg>
            <value>myQueue</value>
        </constructor-arg>
    </bean>

    <!-- 配置JMS模板（Queue），Spring提供的JMS工具类，它发送、接收消息。 -->
    <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
        <property name="connectionFactory" ref="connectionFactory" />
        <property name="defaultDestination" ref="queueDestination" />
        <property name="receiveTimeout" value="10000" />
    </bean>

    <!--queue消息生产者 -->
    <bean id="producerService" class="com.example.mq.test.ProducerServiceImpl">
        <property name="jmsTemplate" ref="jmsTemplate"></property>
    </bean>

    <!--queue消息消费者 -->
    <bean id="consumerService" class="com.example.mq.test.ConsumerServiceImpl">
        <property name="jmsTemplate" ref="jmsTemplate"></property>
    </bean>

</beans>
```





第三步：定义消费者和生产者类



生产者类接口ProducerService

```
package com.example.mq.test;

import org.springframework.stereotype.Component;

import javax.jms.Destination;

public interface ProducerService {

    /**
     * 发消息，向默认的 destination
     * @param msg String 消息内容
     */
    public void sendMessage(String msg);

    /**
     * 发消息，向指定的 destination
     * @param destination 目的地
     * @param msg String 消息内容
     */
    public void sendMessage(Destination destination, String msg);

    /**
     * 发消息，向指定的 destination
     * @param destination 目的地
     * @param msg String 消息内容
     */

    /**
     * 向指定的destination发送消息，消费者接受消息后，把回复的消息写到response队列
     * @param destination 目的地
     * @param msg String 消息内容
     * @param response 回复消息的队列
     */
    public void sendMessage(Destination destination, String msg, Destination response);
}
```



生产者类实现

```
package com.example.mq.test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class ProducerServiceImpl implements ProducerService{

    @Autowired
    private JmsTemplate jmsTemplate;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * 向指定队列发送消息
     */
    public void sendMessage(Destination destination, final String msg) {
        System.out.println("向队列" + destination.toString() + "发送了消息------------" + msg);
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    /**
     * 向默认队列发送消息
     */
    public void sendMessage(final String msg) {
        String destination =  jmsTemplate.getDefaultDestination().toString();
        System.out.println("向队列" +destination+ "发送了消息------------" + msg);
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });

    }

    @Override
    public void sendMessage(Destination destination, String msg, Destination response) {

    }
}
```



消费者类



```
package com.example.mq.test;

import javax.jms.Destination;

public interface ConsumerService {

    public void receive(Destination queueDestination);

}
```



消费者类实现



```
package com.example.mq.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

public class ConsumerServiceImpl implements ConsumerService{

    @Autowired
    private JmsTemplate jmsTemplate;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * 接受消息
     */
    public void receive(Destination destination) {
        TextMessage tm = (TextMessage) jmsTemplate.receive(destination);
        try {
            System.out.println("从队列" + destination.toString() + "收到了消息：\t" + tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
```



上面的消费者也可以用 监听器来实现，在上面的配置文件中，添加如下配置：




    <!-- 配置消息队列监听者（Queue），代码下面给出，只有一个onMessage方法 -->
    <bean id="queueMessageListener" class="com.example.mq.test.QueueMessageListener" />
    
    <!-- 消息监听容器（Queue），配置连接工厂，监听的队列是queue2，监听器是上面定义的监听器 -->
    <bean id="jmsContainer"  class="org.springframework.jms.listener.DefaultMessageListenerContainer">
        <property name="connectionFactory" ref="connectionFactory" />
        <property name="destination" ref="queueDestination" />
        <property name="messageListener" ref="queueMessageListener" />
    </bean>


创建消费者监听器类：



```
package com.example.mq.test;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueMessageListener implements MessageListener {

    //当收到消息时，自动调用该方法。
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            System.out.println("ConsumerMessageListener收到了文本消息：\t" + tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
```

需要把之前的消费者接口和实现类删除掉



第四步：运行测试类



```
package com.example.demo;

import com.example.mq.test.ConsumerService;
import com.example.mq.test.ProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   @Autowired
   private ProducerService producerService;
   @Autowired
   private ConsumerService consumerService;
   @Autowired
   private Destination queueDestination;

   @Test
   public void myMQProductorTest(){
      producerService.sendMessage("hello ay....");
   }

   @Test
   public void myMQComsumerTest(){
      consumerService.receive(queueDestination);
   }

}
```





### **Spring Boot定时器使用**



第一步：在入口类中加入@EnableScheduling注解：



```
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication{

   @Autowired
   private JmsTemplate jmsTemplate;

   public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
   }
}
```

第二步：创建定时器类，类中的方法注解了@Scheduled就是一个定时器：



```
package com.example.quartz.test;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks{

    @Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime(){
        System.out.println ("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date()));
    }

    //每1分钟执行一次
    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentByCron(){
        System.out.println ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date ()));
    }

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat ("HH:mm:ss");
    }

}
```

 没了，没错，使用spring的定时任务就这么简单，其中有几个比较重要的注解：

@EnableScheduling：标注启动定时任务。

@Scheduled(fixedRate = 1000 * 30)  定义某个定时任务。



**第三步：运行项目**



##### **参考文档**

【1】http://blog.csdn.net/clementad/article/details/51955705



### **Spring Boot 整合Swagger**



**第一步：添加Swagger2依赖**

在`pom.xml`中加入Swagger2的依赖

```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.2.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.2.2</version>
</dependency>
```



**第二步：创建Swagger2配置**



在`Application.java`同级创建Swagger2的配置类`Swagger2`。

```
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.didispace.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
                .termsOfServiceUrl("http://blog.didispace.com/")
                .contact("程序猿DD")
                .version("1.0")
                .build();
    }

}
```

如上代码所示，通过`@Configuration`注解，让Spring来加载该类配置。再通过`@EnableSwagger2`注解来启用Swagger2。

再通过`createRestApi`函数创建`Docket`的Bean之后，`apiInfo()`用来创建该Api的基本信息（这些基本信息会展现在文档页面中）。`select()`函数返回一个`ApiSelectorBuilder`实例用来控制哪些接口暴露给Swagger来展现，本例采用指定扫描的包路径来定义，Swagger会扫描该包下所有Controller定义的API，并产生文档内容（除了被`@ApiIgnore`指定的请求）。



**第三步：测试类**

```
package com.example.demo;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ay
 * @date   2017/1/24.
 */
@RestController
@RequestMapping(value="/hello")
@EnableAutoConfiguration
public class AyTestController {

    @RequestMapping("/ay")
    @ApiOperation(value = "ay",httpMethod ="GET", response = String.class,notes = "index")
    public String index(){
        return "Hello Ay...";
    }

    @RequestMapping("/test")
    @ApiOperation(value = "test",httpMethod ="GET", response = String.class,notes = "index")
    public String test(){
        return "Hello Ay...";
    }
}
```



完成上述代码添加上，启动Spring Boot程序，访问：[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
。就能看到前文所展示的RESTful API的页面。我们可以再点开具体的API请求，以POST类型的/users请求为例，可找到上述代码中我们配置的Notes信息以及参数user的描述信息，如下图所示。



在完成了上述配置后，其实已经可以生产文档内容，但是这样的文档主要针对请求本身，而描述主要来源于函数等命名产生，对用户并不友好，我们通常需要自己增加一些说明来丰富文档内容。如下所示，我们通过`@ApiOperation`注解来给API增加说明、通过`@ApiImplicitParams`、`@ApiImplicitParam`注解来给参数增加说明。



### ![QQ截图20170727135453](C:\Users\huangwy\Desktop\md-image\QQ截图20170727135453.png)**Spring Boot实现过滤器filter**



**第一步：定义MyFilter过滤器**

```
package org.springboot.sample.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**

- 使用注解标注过滤器
- @WebFilter将一个实现了javax.servlet.Filter接口的类定义为过滤器
- 属性filterName声明过滤器的名称,可选
- 属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
  */
  @WebFilter(filterName="myFilter",urlPatterns="/*")
  public class MyFilter implements Filter {
  @Override
  public void destroy() {
      System.out.println("过滤器销毁");
  }
  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
          FilterChain chain) throws IOException, ServletException {
      System.out.println("执行过滤操作");
      chain.doFilter(request, response);
  }
  @Override
  public void init(FilterConfig config) throws ServletException {
      System.out.println("过滤器初始化");
  }

}

```

**第二步：在入口类添加@ServletComponentScan 注解**



```
package com.example;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ImportResource(locations={"classpath:spring-mvc.xml"})
@EnableScheduling
//加上这个注解，注意看这里
@ServletComponentScan
public class DemoApplication{

   @Autowired
   private JmsTemplate jmsTemplate;

   public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
   }


}
```

第三步：重启项目，随便访问一个路径，既可以被过滤器拦截到。





### **Spring Boot实现监听器Listener**



第一步：定义监听器类



```
package com.example.listener.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 使用@WebListener注解，实现ServletContextListener接口
 * @author   Ay
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContex初始化");
        System.out.println(sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContex销毁");
    }

}
```



**第二步：在入口类添加@ServletComponentScan 注解**



**第三步：重启系统**





### **Spring Boot全局异常处理**



全局处理异常的：@ControllerAdvice：包含@Component，可以被扫描到。

统一处理异常。@ExceptionHandler（Exception.class）：用在方法上面表示遇到这个异常就执行以下方法。


```
package com.example.exception.test;

import org.springframework.beans.TypeMismatchException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;

@ControllerAdvice
//如果返回的为json数据或其它对象，添加该注解
@ResponseBody
public class GlobalDefaultExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler({NullPointerException.class,NumberFormatException.class})
    public ModelAndView formatErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error","参数类型错误");
        mav.addObject("exception", e);
        mav.addObject("url", "");
        mav.addObject("timestamp", new Date());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

}
```