package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;

public interface AddcommentService {
    public Result addAddcomment(int userid, int articleid, int actorid,int commentid, String content, String time_,String name_);
    public Result deleteAddcomment(int userid,int id,int articleid);
    public Result getAddcomments(int userid,int articleid,int commentid);//已经按照最近到最久顺序排好
    public Result changegoodnum(int userid,int id,int articleid,int count);
}
