package com.ownblog.caelog.service.impl;

import com.ownblog.caelog.Dao.AddcommentDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Addcomment;
import com.ownblog.caelog.service.AddcommentService;
import com.ownblog.caelog.utils.BatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AddcommentServiceImpl implements AddcommentService {
    @Override
    public Result addAddcomment(int userid, int articleid, int actorid, int commentid, String content, String time_,String name_) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        AddcommentDao addcommentDao=sqlSession.getMapper(AddcommentDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Addcomment> addcomments=addcommentDao.getAddcommentlistbyuserid(hashMap);
        int id_=-1;
        for(int i=0;i<addcomments.size();i++){
            if(addcomments.get(i).getId()>id_){
                id_=addcomments.get(i).getId();
            }
        }
        Addcomment addcomment=new Addcomment(userid,id_+1,articleid,commentid,actorid,content,name_,0,time_);
        addcommentDao.insertAddcomment(addcomment);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(actorid);
    }

    @Override
    public Result deleteAddcomment(int userid, int id,int articleid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        AddcommentDao addcommentDao=sqlSession.getMapper(AddcommentDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        hashMap.put("articleid",articleid);
        addcommentDao.deleteAddcomment(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("delete successfully!");
    }

    @Override
    public Result getAddcomments(int userid, int articleid, int commentid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        AddcommentDao addcommentDao=sqlSession.getMapper(AddcommentDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("articleid",articleid);
        hashMap.put("commentid",commentid);
        List<Addcomment> addcomments=addcommentDao.getAddcommentlist(hashMap);
        Collections.sort(addcomments);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(addcomments);
    }

    @Override
    public Result changegoodnum(int userid, int id, int articleid, int count) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        AddcommentDao addcommentDao=sqlSession.getMapper(AddcommentDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        hashMap.put("articleid",articleid);
        hashMap.put("count",count);
        addcommentDao.changegoodnum(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("delete successfully!");
    }
}
