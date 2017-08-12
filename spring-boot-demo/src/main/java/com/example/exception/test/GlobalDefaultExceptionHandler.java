package com.example.exception.test;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
//如果返回的为json数据或其它对象，添加该注解
//@ResponseBody
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
