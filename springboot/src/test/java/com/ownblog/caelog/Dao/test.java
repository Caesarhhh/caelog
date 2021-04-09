package com.ownblog.caelog.Dao;


import com.ownblog.caelog.controller.UserController;
import com.ownblog.caelog.pojo.User;
import com.ownblog.caelog.utils.BatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;

public class test {
    private static  Logger logger=Logger.getLogger(Test.class);
    @Test
    public void test(){
        logger.debug("debug");
//        SqlSession sqlSession= BatisUtils.getSqlSession();
//        UserDao userDao=sqlSession.getMapper(UserDao.class);
//        HashMap map=new HashMap();
//        List<User> userList=userDao.getUserList();
//        for(User user:userList){
//            System.out.println(user);
//        }
//        sqlSession.close();
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        UserController userController=(UserController)ctx.getBean("userController");

    }
}
