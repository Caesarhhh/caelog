package com.ownblog.caelog.pojo;

import com.ownblog.caelog.pojo.tools.Time;

public class Announcement {
    private int userid;
    private int id;
    private String times;
    private String content;
    private String title;
    private int index_;

    public Announcement() {
    }

    public Announcement(int userid, int id, String times, String content, String title, int index_) {
        this.userid = userid;
        this.id = id;
        this.times = times;
        this.content = content;
        this.title = title;
        this.index_ = index_;
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

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex_() {
        return index_;
    }

    public void setIndex_(int index_) {
        this.index_ = index_;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "userid=" + userid +
                ", id=" + id +
                ", times=" + times +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", index_=" + index_ +
                '}';
    }
}
