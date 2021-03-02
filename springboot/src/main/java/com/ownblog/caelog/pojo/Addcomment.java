package com.ownblog.caelog.pojo;

import com.ownblog.caelog.pojo.tools.Time;

public class Addcomment implements Comparable<Addcomment>{
    private int userid;
    private int id;
    private int articleid;
    private int commentid;
    private int actorid;
    private String content;
    private String name_;
    private int goodnum;
    private String time_;

    public Addcomment(int userid, int id, int articleid, int commentid, int actorid, String content, String name_, int goodnum, String time_) {
        this.userid = userid;
        this.id = id;
        this.articleid = articleid;
        this.commentid = commentid;
        this.actorid = actorid;
        this.content = content;
        this.name_ = name_;
        this.goodnum = goodnum;
        this.time_ = time_;
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

    public String getTime() {
        return time_;
    }

    public void setTime(String time) {
        this.time_ = time;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    public String getTime_() {
        return time_;
    }

    public void setTime_(String time_) {
        this.time_ = time_;
    }

    @Override
    public String toString() {
        return "Addcomment{" +
                "userid=" + userid +
                ", id=" + id +
                ", articleid=" + articleid +
                ", commentid=" + commentid +
                ", actorid=" + actorid +
                ", content='" + content + '\'' +
                ", name_='" + name_ + '\'' +
                ", goodnum=" + goodnum +
                ", time_='" + time_ + '\'' +
                '}';
    }

    @Override
    public int compareTo(Addcomment o) {
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
