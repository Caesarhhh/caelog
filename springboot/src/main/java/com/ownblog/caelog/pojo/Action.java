package com.ownblog.caelog.pojo;

public class Action {
    private String type_;
    private int actorid;
    private int targetid;
    private int objectid;
    public Action(){
    }

    public Action(String type, int actorid, int targetid, int objectid) {
        this.type_ = type;
        this.actorid = actorid;
        this.targetid = targetid;
        this.objectid = objectid;
    }

    public String getType_() {
        return type_;
    }

    public void setType_(String type) {
        this.type_ = type;
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

    public int getObjectid() {
        return objectid;
    }

    public void setObjectid(int objectid) {
        this.objectid = objectid;
    }



    @Override
    public String toString() {
        return "Action{" +
                "type_='" + type_ + '\'' +
                ", actorid=" + actorid +
                ", targetid=" + targetid +
                ", objectid=" + objectid +
                '}';
    }
}
