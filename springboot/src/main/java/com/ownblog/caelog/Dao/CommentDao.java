package com.ownblog.caelog.Dao;

import com.ownblog.caelog.pojo.Comment;

import java.util.HashMap;
import java.util.List;

public interface CommentDao {
    List<Comment> getCommentlist(HashMap hashMap);
    List<Comment> getCommentlistbyuserid(HashMap hashMap);
    void insertComment(Comment comment);
    void deleteComment(HashMap hashMap);
    void addonegood(HashMap hashMap);
}
