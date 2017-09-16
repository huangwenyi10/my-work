package com.example.intellij.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ay on 2017/8/24.
 */
public class GrepConsoleTest {

    static Logger logger = LoggerFactory.getLogger(GrepConsoleTest.class);

    public static void main(String[] args) {

        logger.debug("I am Debug ");
        logger.error("I am Error");
        logger.info("I am info");
        logger.warn("I am warn");


    }
}
