package com.ownblog.caelog.pojo;

public class Action {
    private String type;
    private int actorid;
    private int targetid;
    public Action(){
    }

    public Action(String type, int actorid, int targetid) {
        this.type = type;
        this.actorid = actorid;
        this.targetid = targetid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Action{" +
                "type='" + type + '\'' +
                ", actorid=" + actorid +
                ", targetid=" + targetid +
                '}';
    }
}
