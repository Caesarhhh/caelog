package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;

public interface UserService {
    public Result login(String nickname,String password);
    public Result register(String nickname,String password,String introduction,String backg,String securityQuestion,String securityAnswer,String securityEmail);
    public Result changebg();
    public Result changehead();
    public Boolean testname(String username);
    public Result changePersonlData(int userid,String nickname,String password,String introduction,String backg,String securityQuestion,String securityAnswer,String securityEmail);
    public Result changeSecret(int userid,String oldpassword,String newpassword,String safetyanswerinpu,String codeinput,String emailcode);
    public Result getUserbyid(int userid);
    public Result changeSafetyset(int userid,String safetyq,String safetya,String safetye);
    public Result changeBasedata(int userid,String headimgsrc,String nickname,String introduction);
    public Result getuserimgsrc(int userid);
    public Result getnickname(int userid);
    public Result getuser(int userid);
    public Result safetysetinput(int userid,String oldpassword,String securityAnswer,String emailcode,String codeinput);
    public Result changebc(int userid,int colorNum);
    public Result getbc(int userid);
    public Result getemail(int userid);
    boolean testemail(String emailaddress);
}
