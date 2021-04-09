package com.ownblog.caelog.service.impl;

import com.ownblog.caelog.Dao.UserDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.User;
import com.ownblog.caelog.service.UserService;
import com.ownblog.caelog.utils.BatisUtils;
import com.ownblog.caelog.utils.TokenUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
@Repository("userserviceimpl")
public class UserServiceImpl implements UserService {
    @Override
    public Result login(String nickname,String password) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("nickname",nickname);
        if(userDao.getUserbyname(hashMap).size()==0){
            return Result.fail("nickname is nonexistent");
        }
        User user= userDao.getUserbyname(hashMap).get(0);
        System.out.println(nickname+password);
        System.out.println(user.getPassword());
        if(!password.equals(user.getPassword())){
            return Result.fail("password is uncorrect");
        }
        sqlSession.close();
        user.hiddenforsafety();
        return Result.succ(user);
    }

    @Override
    public Result register(String nickname, String password, String introduction,String backg,String securityQuestion,String securityAnswer,String securityEmail) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        int biggestid=userDao.findbiggestid();
        User user=new User(biggestid+1,nickname,password,introduction,backg,securityQuestion,securityAnswer,securityEmail);
        userDao.insertUser(user);
        sqlSession.close();
        return Result.succ(user);
    }

    @Override
    public Result changebg() {
        return null;
    }

    @Override
    public Result changehead() {
        return null;
    }

    @Override
    public Boolean testname(String username) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        HashMap map=new HashMap();
        map.put("nickname",username);
        System.out.println(username);
        List<User>users=userDao.getUserbyname(map);
        if(users.size()>0){
            return false;
        }
        return true;
    }

    @Override
    public Result changePersonlData(int userid,String nickname,String password,String introduction,String backg,String securityQuestion,String securityAnswer,String securityEmail) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        int biggestid=userDao.findbiggestid();
        User user=new User(userid,nickname,password,introduction,backg,securityQuestion,securityAnswer,securityEmail);
        userDao.updateUser(user);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(user);
    }

    @Override
    public Result changeSecret(int userid,String oldpassword,String newpassword,String safetyanswerinput,String codeinput,String emailcode) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        User user=new User();
        HashMap map=new HashMap();
        map.put("userid",userid);
        user=userDao.getUserbyid(map).get(0);
        String token= TokenUtil.getcode(emailcode);
        if(!token.equals(codeinput)){
            return Result.fail("验证码错误！");
        }
        if(!user.getPassword().equals(oldpassword)){
            return Result.fail("原始密码输入错误！");
        }
        if(!user.getSecurityAnswer().equals(safetyanswerinput)){
            return Result.fail("密保问题回答错误！");
        }
        sqlSession.commit();
        sqlSession.close();
        return changePersonlData(userid, user.getNickname(), newpassword, user.getIntroduction(), user.getBackimgsrc(), user.getSecurityQuestion(),user.getSecurityAnswer(),user.getSecurityEmail());
    }

    @Override
    public Result getUserbyid(int userid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        HashMap map=new HashMap();
        map.put("userid",userid);
        User user=userDao.getUserbyid(map).get(0);
        sqlSession.close();
        user.hiddenforsafety();
        return Result.succ(user);
    }

    @Override
    public Result changeSafetyset(int userid,String safetyq, String safetya, String safetye) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        User user=new User();
        HashMap map=new HashMap();
        map.put("userid",user.getId());
        user=userDao.getUserbyid(map).get(0);
        return changePersonlData(userid, user.getNickname(), user.getPassword(), user.getIntroduction(), user.getBackimgsrc(), safetyq,safetya,safetye);
    }

    @Override
    public Result changeBasedata(int userid, String headimgsrc, String nickname, String introduction) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        User user=new User();
        HashMap map=new HashMap();
        map.put("userid",userid);
        user=userDao.getUserbyid(map).get(0);
        return changePersonlData(userid, nickname, user.getPassword(), introduction, headimgsrc, user.getSecurityQuestion(),user.getSecurityAnswer(),user.getSecurityEmail());
    }

    @Override
    public Result getuserimgsrc(int userid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        String imgsrc=userDao.getimgsrc(userid).get(0);
        sqlSession.close();
        return Result.succ(imgsrc);
    }

    @Override
    public Result getnickname(int userid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        String nickname=userDao.getnickname(userid).get(0);
        sqlSession.close();
        return Result.succ(nickname);
    }

    @Override
    public Result getuser(int userid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        User user=userDao.getUserbyid(hashMap).get(0);
        sqlSession.close();
        return Result.succ(user);
    }

    @Override
    public Result safetysetinput(int userid, String oldpassword, String securityAnswer) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        User user=new User();
        HashMap map=new HashMap();
        map.put("userid",user.getId());
        user=userDao.getUserbyid(map).get(0);
        if(!user.getPassword().equals(oldpassword)){
            return Result.fail("原始密码输入错误！");
        }
        if(!user.getSecurityAnswer().equals(securityAnswer)){
            return Result.fail("密保问题回答错误！");
        }
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("succeed!");
    }

    @Override
    public boolean testemail(String emailaddress) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        HashMap map=new HashMap();
        map.put("emailaddress",emailaddress);
        System.out.println(emailaddress);
        List<User>users=userDao.getUserbyemail(map);
        if(users.size()>0){
            return false;
        }
        return true;
    }
}
