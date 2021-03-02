package com.ownblog.caelog.Dao;

import com.ownblog.caelog.pojo.User;

import java.util.HashMap;
import java.util.List;


public interface UserDao {
    List<User> getUserList();
    void insertUser(User user);
    void deleteUser(int id);
    void updateUser(User user);
    int findbiggestid();
    List<User> getUserbyid(HashMap map);
    String getpasswordbyid(HashMap map);
    List<User>getUserbyname(HashMap map);
    List<String>getimgsrc(int userid);
    List<String>getnickname(int userid);
}
