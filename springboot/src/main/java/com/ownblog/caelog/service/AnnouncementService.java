package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.tools.Time;

public interface AnnouncementService {
    public Result addannouncement(int userid, String times, String content, String title);
    public Result deletedannouncement(int userid,int id);
    public Result changeindex(int userid,int id1,int id2);
    public Result getdannouncement(int userid);
}
