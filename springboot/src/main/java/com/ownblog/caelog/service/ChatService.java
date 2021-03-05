package com.ownblog.caelog.service;

import com.ownblog.caelog.lang.Result;

public interface ChatService {
    public Result addchat(int person1id,int person2id);
    public Result changeifview(int id,int ifview);
    public Result deletechat(int id);
    public Result getchat(int person1id,int person2id);
    public Result getchatbyoneid(int userid);
    public Result getbysort(int userid);
}
