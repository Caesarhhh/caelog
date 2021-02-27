package com.ownblog.caelog.pojo;

public class Chat {
    private int person1id;
    private int person2id;
    private int id;
    private int ifview;//0代表没看过，1代表看过.

    public Chat() {
    }

    public Chat(int person1id, int person2id, int id, int ifview) {
        this.person1id = person1id;
        this.person2id = person2id;
        this.id = id;
        this.ifview = ifview;
    }

    public int getPerson1id() {
        return person1id;
    }

    public void setPerson1id(int person1id) {
        this.person1id = person1id;
    }

    public int getPerson2id() {
        return person2id;
    }

    public void setPerson2id(int person2id) {
        this.person2id = person2id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIfview() {
        return ifview;
    }

    public void setIfview(int ifview) {
        this.ifview = ifview;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "person1id=" + person1id +
                ", person2id=" + person2id +
                ", id=" + id +
                ", ifview=" + ifview +
                '}';
    }
}
