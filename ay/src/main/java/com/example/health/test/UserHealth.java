package com.example.health.test;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class UserHealth implements HealthIndicator {
    /**
     * user监控 访问: http://localhost:8080/health
     * @return 自定义Health监控
     */
    @Override
    public Health health() {

        return new Health.Builder().withDetail("usercount", 10) //自定义监控内容
                .withDetail("userstatus", "up").up().build();
    }
}
