package com.ownblog.caelog.pojo;

import com.ownblog.caelog.pojo.tools.Time;

public class Change {
    private int userid;
    private int id;
    private Time time_;
    private String content;

    public Change() {
    }

    public Change(int userid, int id, Time time_, String content) {
        this.userid = userid;
        this.id = id;
        this.time_ = time_;
        this.content = content;
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

    public Time getTime_() {
        return time_;
    }

    public void setTime_(Time time_) {
        this.time_ = time_;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Change{" +
                "userid=" + userid +
                ", id=" + id +
                ", time_=" + time_ +
                ", content='" + content + '\'' +
                '}';
    }
}
