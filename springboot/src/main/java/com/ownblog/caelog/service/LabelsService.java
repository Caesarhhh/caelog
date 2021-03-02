package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;

public interface LabelsService {
    public Result addlabels(int userid,String name,int articleid);
    public Result deletelabels(int userid,int id);
    public Result getlabels(int userid);
    public Result getlabelsar(int userid,int articleid);
}
