package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;

public interface CommentService {
    public Result addComment(int userid,int articleid,int actorid,String content,String time_);
    public Result deleteComment(int userid,int id,int articleid);
    public Result getComments(int userid,int articleid);//已经按照最近到最久顺序排好
    public Result changegoodnum(int userid,int id,int articleid,int count);
}
