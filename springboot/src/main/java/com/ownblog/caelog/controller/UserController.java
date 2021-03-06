package com.ownblog.caelog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ownblog.caelog.Dao.UserDao;
import com.ownblog.caelog.lang.Result;
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
    @Autowired
    private UserServiceImpl userserviceimpl;
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Object register(@RequestBody Map<String,Object> para){
        String username=(String)para.get("nickname");
        String password=(String)para.get("password");
        String introduction=(String)para.get("introduction");
        String backg=(String)para.get("backgroundimgsrc");
        String securityQuestion=(String)para.get("securityQuestion");
        String securityAnswer=(String)para.get("securityAnswer");
        String securityEmail=(String)para.get("securityEmail");
        String emailcode=(String)para.get("emailcode");
        String verifyemail=(String)para.get("verifyemail");
        String codeinput=(String)para.get("inputcode");
        String token= TokenUtil.getcode(emailcode);
        if(!(token.equals(codeinput)&&TokenUtil.getcode(verifyemail).equals(securityEmail))){
            return Result.fail("验证码错误！");
        }
        return userserviceimpl.register(username,password,introduction,backg,securityQuestion,securityAnswer,securityEmail);
    }
    @RequestMapping(value="/test",method = RequestMethod.POST)
    @ResponseBody
    public Object test(){
        return "test";
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody Map<String,Object> para)throws JsonProcessingException {
        String username=(String)para.get("nickname");
        String password=(String)para.get("password");
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
    @RequestMapping(value="/testname",method = RequestMethod.POST)
    public Object testname(@RequestBody Map<String,Object> para)throws JsonProcessingException{
        String username=(String)para.get("username");
        if(userserviceimpl.testname(username)){
            return Result.succ("Nickname is effective!");
        }
        else{
            return Result.fail("Duplicate nicknames exist!");
        }
    }
    @RequestMapping(value="/testemail",method = RequestMethod.POST)
    public Object testemail(@RequestBody Map<String,Object> para)throws JsonProcessingException{
        String emailaddress=(String)para.get("emailaddress");
        if(userserviceimpl.testemail(emailaddress)){
            return Result.succ("E-mail address is effective!");
        }
        else{
            return Result.fail("Duplicate e-mail address exist!");
        }
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
        return userserviceimpl.changeSecret(userid,oldpassword,newpassword,safetyanswerinput,codeinput,emailcode);
    }
    @RequestMapping(value="/safetysetinput",method=RequestMethod.POST)
    @ResponseBody
    public Object safetysetinput(@RequestBody Map<String,Object> para){
        int userid=(int)para.get("userid");
        String oldpassword=(String)para.get("oldpassword");
        String safetyanswerinput=(String)para.get("safetyanswerinput");
        String emailcode=(String)para.get("emailcode");
        String codeinput=(String)para.get("codeinput");
        return userserviceimpl.safetysetinput(userid,oldpassword,safetyanswerinput,emailcode,codeinput);
    }
    @RequestMapping(value="/changebc",method=RequestMethod.POST)
    @ResponseBody
    public Object changebc(@RequestBody Map<String,Object> para){
        int userid= (int) para.get("userid");
        int colorNum=(int)para.get("colorNum");
        return userserviceimpl.changebc(userid,colorNum);
    }
    @RequestMapping(value="/getbc",method=RequestMethod.POST)
    @ResponseBody
    public Object getbc(@RequestBody Map<String,Object> para){
        int userid=Integer.parseInt((String)para.get("userid"));
        return userserviceimpl.getbc(userid);
    }
    @RequestMapping(value="/changesafetyset",method=RequestMethod.POST)
    @ResponseBody
    public Object changesecrets(@RequestBody Map<String,Object> para){
        int userid= (int) para.get("userid");
        String safetyquestion= (String) para.get("safetyquestion");
        String safetyanswer = (String) para.get("safetyanswer");
        String safetyemail= (String) para.get("safetyemail");
        int ifemailchange= (int) para.get("ifemailchange");
        if(ifemailchange==1){
            String emailcode=(String)para.get("emailcode");
            String verifyemail=(String)para.get("verifyemail");
            String codeinput=(String)para.get("inputcode");
            String token= TokenUtil.getcode(emailcode);
            if(!(token.equals(codeinput)&&TokenUtil.getcode(verifyemail).equals(safetyemail))){
                return Result.fail("验证码错误！");
            }

        }
        return userserviceimpl.changeSafetyset(userid,safetyquestion,safetyanswer,safetyemail);
    }
    @RequestMapping(value="/changebasedata",method=RequestMethod.POST)
    @ResponseBody
    public Object changebasedata(@RequestBody Map<String,Object> para){
        int userid= (int) para.get("userid");
        String headimgsrc= (String) para.get("headimgsrc");
        String nickname = (String) para.get("nickname");
        String introduction= (String) para.get("introduction");
        return userserviceimpl.changeBasedata(userid,headimgsrc,nickname,introduction);
    }
    @GetMapping("/get")
    public Object getUserbyid(@RequestParam("userid")int userid){
        return userserviceimpl.getUserbyid(userid);
    }
    @GetMapping("/getemail")
    public Object getemailbyid(@RequestParam("userid")int userid){
        return userserviceimpl.getemail(userid);
    }
    @GetMapping("/getimgsrc")
    public Object getimgsrc(@RequestParam("userid")int userid){
        return userserviceimpl.getuserimgsrc(userid);
    }
    @GetMapping("/getnickname")
    public Object getnickname(@RequestParam("userid")int userid){
        return userserviceimpl.getnickname(userid);
    }
}
