package com.ownblog.caelog.pojo;

import com.ownblog.caelog.pojo.tools.Time;

public class Article {
    private int userid;
    private int id;
    private String title;
    private String content;
    private int blockid;
    private String time_;
    private int goodnum;
    private int viewnum;

    public Article(int userid, int id, String title, String content, int blockid, String time_, int goodnum, int viewnum) {
        this.userid = userid;
        this.id = id;
        this.title = title;
        this.content = content;
        this.blockid = blockid;
        this.time_ = time_;
        this.goodnum = goodnum;
        this.viewnum = viewnum;
    }

    public Article() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBlockid() {
        return blockid;
    }

    public void setBlockid(int blockid) {
        this.blockid = blockid;
    }

    public String getTime_() {
        return time_;
    }

    public void setTime_(String time_) {
        this.time_ = time_;
    }

    public int getGoodnum() {
        return goodnum;
    }

    public void setGoodnum(int goodnum) {
        this.goodnum = goodnum;
    }

    public int getViewnum() {
        return viewnum;
    }

    public void setViewnum(int viewnum) {
        this.viewnum = viewnum;
    }

    @Override
    public String toString() {
        return "Article{" +
                "userid=" + userid +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", blockid=" + blockid +
                ", time_=" + time_ +
                ", goodnum=" + goodnum +
                ", viewnum=" + viewnum +
                '}';
    }
}
