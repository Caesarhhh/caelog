package com.ownblog.caelog.pojo;

import com.ownblog.caelog.pojo.tools.Time;

public class Change implements Comparable<Change>{
    private int userid;
    private int id;
    private String time_;
    private String content;

    public Change() {
    }

    public Change(int userid, int id, String time_, String content) {
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

    public String getTime_() {
        return time_;
    }

    public void setTime_(String time_) {
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

    @Override
    public int compareTo(Change o) {
        Time t2=new Time(this.time_);
        Time t1=new Time(o.time_);
        if(t1.year-t2.year!=0){
            return t1.year-t2.year;
        }
        else{
            if(t1.month-t2.month!=0){
                return t1.month-t2.month;
            }
            else{
                if(t1.day-t2.day!=0){
                    return t1.day-t2.day;
                }
                else{
                    if(t1.hour-t2.hour!=0){
                        return t1.hour-t2.hour;
                    }
                    else{
                        if(t1.min-t2.min!=0){
                            return t1.min-t2.min;
                        }
                        else{
                            return t1.sec-t2.sec;
                        }
                    }
                }
            }
        }
    }
}
