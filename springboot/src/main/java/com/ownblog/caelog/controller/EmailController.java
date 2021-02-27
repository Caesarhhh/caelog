package com.ownblog.caelog.controller;

import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.utils.sendEmailUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.Random;

@RestController
@RequestMapping("/email")
public class EmailController {
    @GetMapping("/send")
    public Object sendrandomcode(@RequestParam("address")String address) throws GeneralSecurityException, MessagingException {
        String code=String.valueOf(Math.round((Math.random()+1) * 1000));
        sendEmailUtil.send(address,"来自Caelog的验证码",code);
        return Result.succ(code);
    }
}
