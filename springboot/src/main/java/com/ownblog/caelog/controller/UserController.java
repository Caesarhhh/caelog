package com.ownblog.caelog.controller;

import com.ownblog.caelog.Dao.UserDao;
import com.ownblog.caelog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/register")
    public Object register(){
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        return userserviceimpl.register("Caesar","164375fox","none","455465456"," "," "," ");
    }
    @GetMapping("/login")
    public Object login(@RequestParam("nickname")String nickname,@RequestParam("password")String password){
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        return userserviceimpl.login(nickname,password);
    }
    @GetMapping("/changepassword")
    public Object changesecret(@RequestParam("userid")int userid,@RequestParam("password")String password){
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        return userserviceimpl.changeSecret(userid,password);
    }
    @GetMapping("/get")
    public Object getUserbyid(@RequestParam("userid")int userid){
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        return userserviceimpl.getUserbyid(userid);
    }
    @GetMapping("/changesafetyset")
    public Object changesecret(@RequestParam("userid")int userid,@RequestParam("safetyquestion")String safetyquestion,@RequestParam("safetyanswer")String safetyanswer,@RequestParam("safetyemail")String safetyemail){
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        return userserviceimpl.changeSafetyset(userid,safetyquestion,safetyanswer,safetyemail);
    }
    @GetMapping("/changebasedata")
    public Object changebasedata(@RequestParam("userid")int userid,@RequestParam("headimgsrc")String headimgsrc,@RequestParam("nickname")String nickname,@RequestParam("introduction")String introduction){
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        return userserviceimpl.changeBasedata(userid,headimgsrc,nickname,introduction);
    }
}
