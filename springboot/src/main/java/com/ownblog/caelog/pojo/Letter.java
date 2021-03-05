package com.ownblog.caelog.pojo;

import com.ownblog.caelog.pojo.tools.Time;

public class Letter implements Comparable<Letter>{
    private int chatid;
    private String time_;
    private int actorid;
    private int targetid;
    private String content;

    public Letter() {
    }

    public Letter(int chatid, String time_, int actorid, int targetid, String content) {
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

    public String getTime_() {
        return time_;
    }

    public void setTime_(String time_) {
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

    @Override
    public int compareTo(Letter o) {
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
