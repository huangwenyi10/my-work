package com.example.mail.test;

import com.example.intellij.test.AyTest;
import com.example.intellij.test.AyTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;

/**
 * Created by Ay on 2017/1/24.
 */
@RestController
@RequestMapping("/mail")
public class MailTestController {

    @Autowired
    JavaMailSender mailSender;

    @RequestMapping("/sendMail")
    public String sendMail() {
        try
        {
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom("huangwenyi10@163.com");
            message.setTo("ay_test@163.com");
            message.setSubject("ay测试邮件");
            message.setText("hello，ay");
            this.mailSender.send(mimeMessage);

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }


}
