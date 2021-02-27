package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;

public interface UserService {
    public Result login(String nickname,String password);
    public Result register(String nickname,String password,String introduction,String backg,String securityQuestion,String securityAnswer,String securityEmail);
    public Result changebg();
    public Result changehead();
    public Result changePersonlData(int userid,String nickname,String password,String introduction,String backg,String securityQuestion,String securityAnswer,String securityEmail);
    public Result changeSecret(int userid,String secret);
    public Result getUserbyid(int userid);
    public Result changeSafetyset(int userid,String safetyq,String safetya,String safetye);
    public Result changeBasedata(int userid,String headimgsrc,String nickname,String introduction);
}
