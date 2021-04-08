package com.ownblog.caelog.service.impl;

import com.ownblog.caelog.Dao.AnnouncementDao;
import com.ownblog.caelog.Dao.SocialhrefDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Announcement;
import com.ownblog.caelog.pojo.Socialhref;
import com.ownblog.caelog.pojo.tools.Time;
import com.ownblog.caelog.service.AnnouncementService;
import com.ownblog.caelog.utils.BatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class AnnouncementServiceImpl implements AnnouncementService {
    @Override
    public Result addannouncement(int userid, String times, String content, String title) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        AnnouncementDao announcementDao=sqlSession.getMapper(AnnouncementDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Announcement> announcements=announcementDao.getAnnouncementlist(hashMap);
        int id=-1;
        for(int i=0;i<announcements.size();i++){
            if(announcements.get(i).getId()>id){
                id=announcements.get(i).getId();
            }
        }
        Announcement announcement=new Announcement(userid,id+3,times,content,title,announcements.size()+1);
        announcementDao.insertAnnouncement(announcement);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(announcement);
    }

    @Override
    public Result deletedannouncement(int userid, int id) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        AnnouncementDao announcementDao=sqlSession.getMapper(AnnouncementDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Announcement> announcementss=announcementDao.getAnnouncementlist(hashMap);
        int len=announcementss.size();
        hashMap.put("id",id);
        List<Announcement> announcements=announcementDao.getAnnouncementlistbyid(hashMap);
        Announcement announcement=new Announcement();
        announcement=announcements.get(0);
        int poindex=announcement.getIndex_();
        announcementDao.deleteAnnouncement(hashMap);
        for(int i=poindex+1;i<len+1;i++){
            HashMap tempmap=new HashMap();
            tempmap.put("index_",i);
            tempmap.put("userid",userid);
            List<Announcement>temps=announcementDao.getAnnouncementlistbyindex(tempmap);
            tempmap.replace("index_",Integer.parseInt(tempmap.get("index_").toString())-1);
            tempmap.put("id",temps.get(0).getId());
            tempmap.put("times",temps.get(0).getTimes());
            tempmap.put("title",temps.get(0).getTitle());
            tempmap.put("content",temps.get(0).getContent());
            announcementDao.updateAnnouncement(tempmap);
        }
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("delete successfully!");
    }

    @Override
    public Result changeindex(int userid, int index1, int index2) {
        System.out.println(index1);
        System.out.println(index2);
        SqlSession sqlSession= BatisUtils.getSqlSession();
        AnnouncementDao announcementDao=sqlSession.getMapper(AnnouncementDao.class);
        HashMap hashMap1=new HashMap();
        HashMap hashMap2=new HashMap();
        hashMap1.put("userid",userid);
        hashMap2.put("userid",userid);
        List<Announcement> announcements=announcementDao.getAnnouncementlist(hashMap1);
        Announcement announcement1=new Announcement();
        Announcement announcement2=new Announcement();
        for(int i=0;i<announcements.size();i++){
            if(announcements.get(i).getIndex_()==index1){
                announcement1=announcements.get(i);
            }
            if(announcements.get(i).getIndex_()==index2){
                announcement2=announcements.get(i);
            }
        }
        hashMap1.put("id",announcement1.getId());
        hashMap1.put("times",announcement1.getTimes());
        hashMap1.put("title",announcement1.getTitle());
        hashMap1.put("content",announcement1.getContent());
        hashMap1.put("index_",announcement2.getIndex_());
        hashMap2.put("id",announcement2.getId());
        hashMap2.put("times",announcement2.getTimes());
        hashMap2.put("title",announcement2.getTitle());
        hashMap2.put("content",announcement2.getContent());
        hashMap2.put("index_",announcement1.getIndex_());
        announcementDao.updateAnnouncement(hashMap1);
        announcementDao.updateAnnouncement(hashMap2);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("change successfully!");
    }

    @Override
    public Result getdannouncement(int userid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        AnnouncementDao announcementDao=sqlSession.getMapper(AnnouncementDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Announcement> announcements=announcementDao.getAnnouncementlist(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(announcements);
    }
}
