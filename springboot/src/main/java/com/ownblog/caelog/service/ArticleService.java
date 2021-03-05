package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;

public interface ArticleService {
    public Result addarticle(int userid, String title, String content, String time_,int blockid);
    public Result deletearticle(int userid,int id);
    public Result getarticle(int userid);
    public Result updatearticle(int userid,int id, String title, String content, int blockid);
    public Result getonearticle(int userid,int id);
    public Result addonegood(int userid,int id,int count);
    public Result getarticlebylabel(int userid,String labelname);
    public Result getarticlebyblock(int userid,String blockname);
    public Result getarticlebysearch(int userid,String searchcontent);
}
