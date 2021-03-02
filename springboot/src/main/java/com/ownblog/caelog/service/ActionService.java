package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;

public interface ActionService {
    public Result addAction(int actorid, int targetid, String type_,int objectid);
    public Result deleteAction(int actorid, int targetid, String type_,int objectid);
    public Result getAction(int actorid);
    public Result ifin(int actorid, int targetid, String type_,int objectid);
}
