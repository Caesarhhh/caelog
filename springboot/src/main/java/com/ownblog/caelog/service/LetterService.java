package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;

public interface LetterService {
    public Result addletter(int actorid, int targetid, String content, String time);
    public Result deleteletter(int chatid);
    public Result getletters(int chatid);
}
