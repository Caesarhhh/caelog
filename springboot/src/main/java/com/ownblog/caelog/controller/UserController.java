package com.ownblog.caelog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ownblog.caelog.Dao.UserDao;
import com.ownblog.caelog.pojo.User;
import com.ownblog.caelog.service.impl.UserServiceImpl;
import com.ownblog.caelog.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user",method = RequestMethod.POST)
public class UserController {
    @GetMapping("/register")
    public Object register(){
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        return userserviceimpl.register("Caesar","164375fox","none","455465456"," "," "," ");
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody Map<String,Object> para)throws JsonProcessingException {
        String username=(String)para.get("nickname");
        String password=(String)para.get("password");
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        if(userserviceimpl.login(username,password).getCode()==400){
            return userserviceimpl.login(username,password);
        }
        String token= TokenUtil.sign(new User(username,password));
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("token",token);
        hs.put("userinfo",userserviceimpl.login(username,password));
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
    @RequestMapping(value="/test",method=RequestMethod.GET)
    @ResponseBody
    public String test(@RequestParam("test")int test) throws JsonProcessingException {
        System.out.println(test);
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
    @RequestMapping(value="/changepassword",method=RequestMethod.POST)
    @ResponseBody
    public Object changesecret(@RequestBody Map<String,Object> para){
        int userid=(int)para.get("userid");
        String oldpassword=(String)para.get("oldpassword");
        String newpassword=(String)para.get("newpassword");
        String codeinput=(String)para.get("codeinput");
        String safetyanswerinput=(String)para.get("safetyanswerinput");
        String emailcode=(String)para.get("emailcode");
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        return userserviceimpl.changeSecret(userid,oldpassword,newpassword,safetyanswerinput,codeinput,emailcode);
    }
    @RequestMapping(value="/safetysetinput",method=RequestMethod.POST)
    @ResponseBody
    public Object safetysetinput(@RequestBody Map<String,Object> para){
        int userid=(int)para.get("userid");
        String oldpassword=(String)para.get("oldpassword");
        String safetyanswerinput=(String)para.get("safetyanswerinput");
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        return userserviceimpl.safetysetinput(userid,oldpassword,safetyanswerinput);
    }
    @GetMapping("/get")
    public Object getUserbyid(@RequestParam("userid")int userid){
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        return userserviceimpl.getUserbyid(userid);
    }
    @GetMapping("/getimgsrc")
    public Object getimgsrc(@RequestParam("userid")int userid){
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        return userserviceimpl.getuserimgsrc(userid);
    }
    @GetMapping("/getnickname")
    public Object getnickname(@RequestParam("userid")int userid){
        UserServiceImpl userserviceimpl=new UserServiceImpl();
        return userserviceimpl.getnickname(userid);
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
