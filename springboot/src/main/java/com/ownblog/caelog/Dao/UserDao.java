package com.ownblog.caelog.Dao;

import com.ownblog.caelog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;


public interface UserDao extends JpaRepository<User,Long> {
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
