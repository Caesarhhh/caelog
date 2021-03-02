package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;

public interface ChangeService {
    public Result addChange(int userid, String content,String time_);
    public Result deleteChange(int userid,int id);
    public Result getChange(int userid);//已经按照最近到最久顺序排好
}
