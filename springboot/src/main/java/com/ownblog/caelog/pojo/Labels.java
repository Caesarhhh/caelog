package com.ownblog.caelog.pojo;

public class Labels {
    private int id;
    private int userid;
    private int articleid;
    private String name_;

    public Labels() {
    }

    public Labels(int id, int userid, int articleid, String name_) {
        this.id = id;
        this.userid = userid;
        this.articleid = articleid;
        this.name_ = name_;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
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

    @Override
    public String toString() {
        return "Labels{" +
                "id=" + id +
                ", userid=" + userid +
                ", articleid=" + articleid +
                ", name_='" + name_ + '\'' +
                '}';
    }
}
