package com.example.thymeleaf.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Created by Ay on 2017/1/24.
 */
/**
 * 这里使用@Controller,而不是@RestController注解
 * 因为@RestController,表示同时使用@Controller和@ResponseBody，所以会返回thymeleaf
 * 而不是返回hello.html的内容。
 * @author howieli
 *
 */
@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafTestController {

    @RequestMapping("/test")
    public String test(Model model) {
        model.addAttribute("name","ay");
        model.addAttribute("love","al");
        return "thymeleaf";
    }


}
