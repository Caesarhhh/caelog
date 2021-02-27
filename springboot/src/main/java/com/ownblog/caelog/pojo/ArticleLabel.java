package com.ownblog.caelog.pojo;

public class ArticleLabel {
    private int articleid;
    private int labelid;

    public ArticleLabel(int articleid, int labelid) {
        this.articleid = articleid;
        this.labelid = labelid;
    }

    public ArticleLabel() {
    }

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public int getLabelid() {
        return labelid;
    }

    public void setLabelid(int labelid) {
        this.labelid = labelid;
    }

    @Override
    public String toString() {
        return "ArticleLabel{" +
                "articleid=" + articleid +
                ", labelid=" + labelid +
                '}';
    }
}
