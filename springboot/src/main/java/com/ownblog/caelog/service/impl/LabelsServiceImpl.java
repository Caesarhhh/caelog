package com.ownblog.caelog.service.impl;

import com.ownblog.caelog.Dao.BlocksDao;
import com.ownblog.caelog.Dao.LabelsDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Blocks;
import com.ownblog.caelog.pojo.Labels;
import com.ownblog.caelog.service.LabelsService;
import com.ownblog.caelog.utils.BatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class LabelsServiceImpl implements LabelsService {
    @Override
    public Result addlabels(int userid, String name,int articleid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        LabelsDao labelsDao=sqlSession.getMapper(LabelsDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Labels> labels=labelsDao.getLabelslistall(hashMap);
        int id_=-1;
        for(int i=0;i<labels.size();i++){
            if(labels.get(i).getId()>id_){
                id_=labels.get(i).getId();
            }
        }
        Labels labelss=new Labels(id_+1,userid,articleid,name);
        labelsDao.insertLabels(labelss);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(labels);
    }

    @Override
    public Result deletelabels(int userid,int id) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        LabelsDao labelsDao=sqlSession.getMapper(LabelsDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        labelsDao.deleteLabels(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("delete successfully!");
    }

    @Override
    public Result getlabels(int userid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        LabelsDao labelsDao=sqlSession.getMapper(LabelsDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Labels> labels=labelsDao.getLabelslist(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(labels);
    }

    @Override
    public Result getlabelsar(int userid, int articleid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        LabelsDao labelsDao=sqlSession.getMapper(LabelsDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("articleid",articleid);
        List<Labels> labels=labelsDao.getLabelslistar(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(labels);
    }
}
