package com.ownblog.caelog.pojo;

public class Blocks {
    private int id;
    private int userid;
    private String imgsrc;
    private  String name_;
    private String remark;

    public Blocks() {
    }

    public Blocks(int id, int userid, String imgsrc, String name_, String remark) {
        this.id = id;
        this.userid = userid;
        this.imgsrc = imgsrc;
        this.name_ = name_;
        this.remark = remark;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    @Override
    public String toString() {
        return "Blocks{" +
                "id=" + id +
                ", userid=" + userid +
                ", imgsrc='" + imgsrc + '\'' +
                ", name_='" + name_ + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
