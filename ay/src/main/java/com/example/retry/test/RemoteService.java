package com.example.retry.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component("remoteService")
public class RemoteService {

    private static final Logger logger = LoggerFactory.getLogger(RemoteService.class);

    @Retryable(value= {BusinessException.class},maxAttempts = 5,backoff = @Backoff(delay = 5000,multiplier = 2))
    public void call() throws Exception {
        logger.info("do something...");
        throw new BusinessException();
    }

    @Recover
    public void recover(BusinessException e) {
        //具体的业务逻辑
        logger.info(" ---------------------------  ");
        logger.info(e.getMessage());
    }
}