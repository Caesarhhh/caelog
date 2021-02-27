package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;

public interface BlocksService {
    public Result addblocks(int userid, String imgsrc,String name_, String remark);
    public Result deleteblocks(int userid,int id);
    public Result getblocks(int userid);
}
