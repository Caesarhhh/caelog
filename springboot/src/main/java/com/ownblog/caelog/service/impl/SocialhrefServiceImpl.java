package com.ownblog.caelog.service.impl;

import com.ownblog.caelog.Dao.SocialhrefDao;
import com.ownblog.caelog.Dao.UserDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Socialhref;
import com.ownblog.caelog.pojo.User;
import com.ownblog.caelog.service.SocialhrefService;
import com.ownblog.caelog.utils.BatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("socialhrefserviceimpl")
public class SocialhrefServiceImpl implements SocialhrefService {

    @Override
    public Result addhref(int userid,String imgsrc, String href, String comment) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        SocialhrefDao socialhrefDao=sqlSession.getMapper(SocialhrefDao.class);
        int ifuse=0;
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Socialhref> socialhrefs=socialhrefDao.getSocialhreflist(hashMap);
        int hrefid=-1;
        for(int i=0;i<socialhrefs.size();i++){
            if(socialhrefs.get(i).getHrefid()>hrefid){
                hrefid=socialhrefs.get(i).getHrefid();
            }
        }
        Socialhref socialhref=new Socialhref(userid,hrefid+1,imgsrc,href,comment,ifuse);
        socialhrefDao.insertSocialhref(socialhref);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(socialhref);
    }

    @Override
    public Result deletehref(int userid, int hrefid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        SocialhrefDao socialhrefDao=sqlSession.getMapper(SocialhrefDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("hrefid",hrefid);
        socialhrefDao.deleteSocialhref(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("delete successfully!");
    }

    @Override
    public Result changevisble(int userid, int hrefid, int ifuse) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        SocialhrefDao socialhrefDao=sqlSession.getMapper(SocialhrefDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Socialhref> socialhrefs=socialhrefDao.getSocialhreflist(hashMap);
        Socialhref socialhref=new Socialhref();
        for(int i=0;i<socialhrefs.size();i++){
            if(socialhrefs.get(i).getHrefid()==hrefid){
                socialhref=socialhrefs.get(i);
            }
        }
        hashMap.put("hrefid",hrefid);
        hashMap.put("imgsrc",socialhref.getImgsrc());
        hashMap.put("href",socialhref.getHref());
        hashMap.put("comment",socialhref.getComment());
        hashMap.put("ifuse",ifuse);
        socialhrefDao.updateSocialhref(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("change successfully!");
    }

    @Override
    public Result gethrefsbyid(int userid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        SocialhrefDao socialhrefDao=sqlSession.getMapper(SocialhrefDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Socialhref> socialhrefs=socialhrefDao.getSocialhreflist(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(socialhrefs);
    }
}
