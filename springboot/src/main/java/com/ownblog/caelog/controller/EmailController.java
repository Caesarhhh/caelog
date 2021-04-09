package com.ownblog.caelog.controller;

import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.User;
import com.ownblog.caelog.service.impl.UserServiceImpl;
import com.ownblog.caelog.utils.TokenUtil;
import com.ownblog.caelog.utils.sendEmailUtil;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping(value="/email",method = RequestMethod.POST)
public class EmailController {
    @RequestMapping(value = "send")
    @ResponseBody
    public Object sendrandomcode(@RequestBody Map<String,Object> para) throws GeneralSecurityException, MessagingException {
        UserServiceImpl userService=new UserServiceImpl();
        int userid=(Integer)para.get("userid");
        User user= (User) userService.getuser(userid).getData();
        String address=user.getSecurityEmail();
        String code=String.valueOf(Math.round((Math.random()+1) * 1000));
        sendEmailUtil.send(address,"来自Caelog的验证码",code);
        return Result.succ(TokenUtil.initcode(code));
    }
    @RequestMapping(value = "sendtemp")
    @ResponseBody
    public Object sendrandomcodetemp(@RequestBody Map<String,Object> para) throws GeneralSecurityException, MessagingException {
        String address= (String) para.get("address");
        String code=String.valueOf(Math.round((Math.random()+1) * 1000));
        sendEmailUtil.send(address,"来自Caelog的验证码",code);
        return Result.succ(TokenUtil.initcode(code));
    }
}
