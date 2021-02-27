package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;

public interface SocialhrefService {
    public Result addhref(int userid,String imgsrc, String href, String comment);
    public Result deletehref(int userid,int hrefid);
    public Result changevisble(int userid,int hrefid,int ifuse);
    public Result gethrefsbyid(int userid);
}
