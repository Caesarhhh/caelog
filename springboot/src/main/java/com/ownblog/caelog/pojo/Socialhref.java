package com.ownblog.caelog.pojo;

public class Socialhref {
    private int userid;
    private int hrefid;
    private String imgsrc;
    private String href;
    private String comment;
    private int ifuse;//0代表未使用，1代表使用

    public Socialhref() {
    }

    public Socialhref(int userid, int hrefid, String imgsrc, String href, String comment, int ifuse) {
        this.userid = userid;
        this.hrefid = hrefid;
        this.imgsrc = imgsrc;
        this.href = href;
        this.comment = comment;
        this.ifuse = ifuse;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getHrefid() {
        return hrefid;
    }

    public void setHrefid(int hrefid) {
        this.hrefid = hrefid;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIfuse() {
        return ifuse;
    }

    public void setIfuse(int ifuse) {
        this.ifuse = ifuse;
    }

    @Override
    public String toString() {
        return "Socialhref{" +
                "userid=" + userid +
                ", hrefid=" + hrefid +
                ", imgsrc='" + imgsrc + '\'' +
                ", href='" + href + '\'' +
                ", comment='" + comment + '\'' +
                ", ifuse=" + ifuse +
                '}';
    }
}
