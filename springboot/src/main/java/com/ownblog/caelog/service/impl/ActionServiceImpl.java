package com.ownblog.caelog.service.impl;

import com.ownblog.caelog.Dao.ActionDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Action;
import com.ownblog.caelog.service.ActionService;
import com.ownblog.caelog.utils.BatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class ActionServiceImpl implements ActionService {
    @Override
    public Result addAction(int actorid, int targetid, String type_, int objectid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ActionDao actionDao=sqlSession.getMapper(ActionDao.class);
        Action action=new Action(type_,actorid,targetid,objectid);
        actionDao.insertAction(action);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(action);
    }

    @Override
    public Result deleteAction(int actorid, int targetid, String type_, int objectid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ActionDao actionDao=sqlSession.getMapper(ActionDao.class);
        Action action=new Action(type_,actorid,targetid,objectid);
        actionDao.deleteAction(action);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(action);
    }

    @Override
    public Result getAction(int actorid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ActionDao actionDao=sqlSession.getMapper(ActionDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("actorid",actorid);
        List<Action> actions=actionDao.getActionListbyactor(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(actions);
    }

    @Override
    public Result ifin(int actorid, int targetid, String type_, int objectid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ActionDao actionDao=sqlSession.getMapper(ActionDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("actorid",actorid);
        hashMap.put("targetid",targetid);
        hashMap.put("type_",type_);
        hashMap.put("objectid",objectid);
        if(actionDao.ifin(hashMap).size()>0){
            sqlSession.commit();
            sqlSession.close();
            return Result.succ("in");
        }
        sqlSession.commit();
        sqlSession.close();
        return Result.fail("notin");
    }
}
