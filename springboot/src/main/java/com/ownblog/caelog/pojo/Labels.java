package com.ownblog.caelog.pojo;

public class Labels {
    private int id;
    private String name_;

    public Labels() {
    }

    public Labels(int id, String name_) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Labels{" +
                "id=" + id +
                ", name_='" + name_ + '\'' +
                '}';
    }
}
