package com.ownblog.caelog.service.impl;

import com.ownblog.caelog.Dao.ChangeDao;
import com.ownblog.caelog.Dao.LabelsDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Change;
import com.ownblog.caelog.pojo.Labels;
import com.ownblog.caelog.service.ChangeService;
import com.ownblog.caelog.utils.BatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ChangeServiceImpl implements ChangeService {

    @Override
    public Result addChange(int userid, String content, String time_) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ChangeDao changeDao=sqlSession.getMapper(ChangeDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Change> changes=changeDao.getChangelist(hashMap);
        int id_=-1;
        for(int i=0;i<changes.size();i++){
            if(changes.get(i).getId()>id_){
                id_=changes.get(i).getId();
            }
        }
        Change change=new Change(userid,id_+20,time_,content);
        changeDao.insertChange(change);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(change);
    }

    @Override
    public Result deleteChange(int userid, int id) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ChangeDao changeDao=sqlSession.getMapper(ChangeDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        changeDao.deleteChange(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("delete successfully!");
    }

    @Override
    public Result getChange(int userid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ChangeDao changeDao=sqlSession.getMapper(ChangeDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Change> change=changeDao.getChangelist(hashMap);
        Collections.sort(change);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(change);
    }
}
