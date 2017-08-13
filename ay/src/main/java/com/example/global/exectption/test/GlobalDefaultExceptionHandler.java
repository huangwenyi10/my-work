package com.example.global.exectption.test;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


//如果返回的为json数据或其它对象，添加该注解
//@ResponseBody
@ControllerAdvice(basePackages={"com.example.demo",})
public class GlobalDefaultExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler({NullPointerException.class,NumberFormatException.class})
    public ModelAndView nullPointErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        //这里是异常捕获到需要处理的业务
        System.out.println("我已经捕获到异常了");
        return null;
    }

}
