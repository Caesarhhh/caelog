package com.ownblog.caelog.Dao;

import com.ownblog.caelog.pojo.Addcomment;

import java.util.HashMap;
import java.util.List;

public interface AddcommentDao {
    List<Addcomment> getAddcommentlist(HashMap hashMap);
    List<Addcomment> getAddcommentlistbyuserid(HashMap hashMap);
    List<Addcomment> getAddcommentlistall(HashMap hashMap);
    void insertAddcomment(Addcomment addcomment);
    void deleteAddcomment(HashMap hashMap);
    void deleteAddcommentbyCommentid(HashMap hashMap);
    void changegoodnum(HashMap hashMap);
}
