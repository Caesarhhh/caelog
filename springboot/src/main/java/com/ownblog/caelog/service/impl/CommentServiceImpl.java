package com.ownblog.caelog.service.impl;

import com.ownblog.caelog.Dao.AddcommentDao;
import com.ownblog.caelog.Dao.CommentDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Comment;
import com.ownblog.caelog.service.CommentService;
import com.ownblog.caelog.utils.BatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CommentServiceImpl implements CommentService {
    @Override
    public Result addComment(int userid, int articleid, int actorid, String content, String time_) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        CommentDao commentDao=sqlSession.getMapper(CommentDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Comment> comments=commentDao.getCommentlistbyuserid(hashMap);
        int id_=-1;
        for(int i=0;i<comments.size();i++){
            if(comments.get(i).getId()>id_){
                id_=comments.get(i).getId();
            }
        }
        Comment comment=new Comment(userid,id_+1,articleid,actorid,content,0,time_);
        commentDao.insertComment(comment);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(comment);
    }

    @Override
    public Result deleteComment(int userid, int id,int articleid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        CommentDao commentDao=sqlSession.getMapper(CommentDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        hashMap.put("articleid",articleid);
        commentDao.deleteComment(hashMap);
        sqlSession.commit();
        sqlSession.close();
        SqlSession sqlSession1= BatisUtils.getSqlSession();
        AddcommentDao addcommentDao=sqlSession1.getMapper(AddcommentDao.class);
        addcommentDao.deleteAddcommentbyCommentid(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("delete successfully!");
    }

    @Override
    public Result getComments(int userid,int articleid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        CommentDao commentDao=sqlSession.getMapper(CommentDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("articleid",articleid);
        List<Comment> comments=commentDao.getCommentlist(hashMap);
        Collections.sort(comments);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(comments);
    }

    @Override
    public Result changegoodnum(int userid, int id, int articleid, int count) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        CommentDao commentDao=sqlSession.getMapper(CommentDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        hashMap.put("articleid",articleid);
        hashMap.put("count",count);
        commentDao.addonegood(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("delete successfully!");
    }

}
