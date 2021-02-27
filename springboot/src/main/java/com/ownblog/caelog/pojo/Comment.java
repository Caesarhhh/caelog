package com.ownblog.caelog.pojo;

import com.ownblog.caelog.pojo.tools.Time;

public class Comment {
    private int id;
    private int articleid;
    private int actorid;
    private String content;
    private int goodnum;
    private Time time_;

    public Comment() {
    }

    public Comment(int id, int articleid, int actorid, String content, int goodnum, Time time_) {
        this.id = id;
        this.articleid = articleid;
        this.actorid = actorid;
        this.content = content;
        this.goodnum = goodnum;
        this.time_ = time_;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
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

    public Time getTime_() {
        return time_;
    }

    public void setTime_(Time time_) {
        this.time_ = time_;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", articleid=" + articleid +
                ", actorid=" + actorid +
                ", content='" + content + '\'' +
                ", goodnum=" + goodnum +
                ", time_=" + time_ +
                '}';
    }
}
