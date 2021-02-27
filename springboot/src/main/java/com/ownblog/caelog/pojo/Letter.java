package com.ownblog.caelog.pojo;

import com.ownblog.caelog.pojo.tools.Time;

public class Letter {
    private int chatid;
    private Time time_;
    private int actorid;
    private int targetid;
    private String content;

    public Letter() {
    }

    public Letter(int chatid, Time time_, int actorid, int targetid, String content) {
        this.chatid = chatid;
        this.time_ = time_;
        this.actorid = actorid;
        this.targetid = targetid;
        this.content = content;
    }

    public int getChatid() {
        return chatid;
    }

    public void setChatid(int chatid) {
        this.chatid = chatid;
    }

    public Time getTime_() {
        return time_;
    }

    public void setTime_(Time time_) {
        this.time_ = time_;
    }

    public int getActorid() {
        return actorid;
    }

    public void setActorid(int actorid) {
        this.actorid = actorid;
    }

    public int getTargetid() {
        return targetid;
    }

    public void setTargetid(int targetid) {
        this.targetid = targetid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Letter{" +
                "chatid=" + chatid +
                ", time_=" + time_ +
                ", actorid=" + actorid +
                ", targetid=" + targetid +
                ", content='" + content + '\'' +
                '}';
    }
}
