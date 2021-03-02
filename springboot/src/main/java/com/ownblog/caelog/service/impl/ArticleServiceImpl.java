package com.ownblog.caelog.service.impl;

import com.ownblog.caelog.Dao.ArticleDao;
import com.ownblog.caelog.Dao.BlocksDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Article;
import com.ownblog.caelog.pojo.Blocks;
import com.ownblog.caelog.service.ArticleService;
import com.ownblog.caelog.utils.BatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class ArticleServiceImpl implements ArticleService {
    @Override
    public Result addarticle(int userid, String title, String content, String time_,int blockid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Article> articles=articleDao.getArticlelist(hashMap);
        int id_=-1;
        for(int i=0;i<articles.size();i++){
            if(articles.get(i).getId()>id_){
                id_=articles.get(i).getId();
            }
        }
        Article article=new Article(userid,id_+1,title,content,blockid,time_,0,0);
        articleDao.insertArticle(article);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(article);
    }

    @Override
    public Result deletearticle(int userid, int id) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        articleDao.deleteArticle(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("delete successfully!");
    }

    @Override
    public Result getarticle(int userid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Article> article=articleDao.getArticlelist(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(article);
    }

    @Override
    public Result updatearticle(int userid, int id,String title, String content, int blockid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        List<Article> articles=articleDao.getArticlelist(hashMap);
        Article at=articles.get(0);
        hashMap.put("title",title);
        hashMap.put("content",content);
        hashMap.put("blockid",blockid);
        hashMap.put("time_",at.getTime_());
        hashMap.put("goodnum",at.getGoodnum());
        hashMap.put("viewnum",at.getViewnum());
        articleDao.updateArticle(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("updata successfully!");
    }

    @Override
    public Result getonearticle(int userid, int id) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        List<Article> article=articleDao.getArticlebyid(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(article);
    }

    @Override
    public Result addonegood(int userid, int id,int count) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        hashMap.put("count",count);
        articleDao.addonegood(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("add one good successfully!");
    }
}
