package com.ownblog.caelog.Dao;

import com.ownblog.caelog.pojo.Article;

import java.util.HashMap;
import java.util.List;

public interface ArticleDao {
    List<Article> getArticlelist(HashMap hashMap);
    void insertArticle(Article article);
    void deleteArticle(HashMap hashMap);
    void deleteArticlebyblock(HashMap hashMap);
    void updateArticle(HashMap hashMap);
    List<Article> getArticlebyid(HashMap hashMap);
    void addonegood(HashMap hashMap);
    void addview(HashMap hashMap);
    List<Article> getArticlelistbyblock(HashMap hashMap);
    List<Article> getArticlelistbyblockid(HashMap hashMap);
    List<Article> getArticlelistbylabel(HashMap hashMap);
    List<Article> getArticlelistbysearch(HashMap hashMap);
}
