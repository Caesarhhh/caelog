package com.ownblog.caelog.pojo;

import com.ownblog.caelog.pojo.tools.Time;

public class Addcomment {
    private int id;
    private int commentid;
    private int actorid;
    private String content;
    private int goodnum;
    private Time time;

    public Addcomment(int id, int commentid, int actorid, String content, int goodnum, Time time) {
        this.id = id;
        this.commentid = commentid;
        this.actorid = actorid;
        this.content = content;
        this.goodnum = goodnum;
        this.time = time;
    }

    public Addcomment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public int getActorid() {
        return actorid;
    }

    public void setActorid(int actorid) {
        this.actorid = actorid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGoodnum() {
        return goodnum;
    }

    public void setGoodnum(int goodnum) {
        this.goodnum = goodnum;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Addcomment{" +
                "id=" + id +
                ", commentid=" + commentid +
                ", actorid=" + actorid +
                ", content='" + content + '\'' +
                ", goodnum=" + goodnum +
                ", time=" + time +
                '}';
    }
}
