package com.ownblog.caelog.service.impl;

import com.ownblog.caelog.Dao.ArticleDao;
import com.ownblog.caelog.Dao.BlocksDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Article;
import com.ownblog.caelog.pojo.Blocks;
import com.ownblog.caelog.service.ArticleService;
import com.ownblog.caelog.utils.BatisUtils;
import com.ownblog.caelog.utils.RedisLockCommon;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisLockCommon redisLock;

    @Override
    public Result addarticle(int userid, String title, String content, String time_,int blockid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        String key="addarticle"+String.valueOf(userid);
        long time = System.currentTimeMillis();
        while(!redisLock.tryLock(key,String.valueOf(time))){
            if(System.currentTimeMillis()-time>10000){
                return Result.fail("添加超时，请稍后再试！");
            }
        }
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
        redisLock.unlock(key,String.valueOf(time));
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
        String key="articles_"+String.valueOf(id)+"_user_"+userid;
        if(redisTemplate.hasKey(key)){
            redisTemplate.delete(key);
        }
        return Result.succ("delete successfully!");
    }

    @Override
    public Result getarticle(int userid) {
//        String key="articles_user_"+userid;
//        ValueOperations<String,List<Article>>operations= redisTemplate.opsForValue();
//        if(redisTemplate.hasKey(key)){
//            List<Article>articles=operations.get(key);
//            return Result.succ(articles);
//        }
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Article> article=articleDao.getArticlelist(hashMap);
        sqlSession.commit();
        sqlSession.close();
//        operations.set(key, article, 5, TimeUnit.HOURS);
        return Result.succ(article);
    }

    @Override
    public Result updatearticle(int userid, int id,String title, String content, int blockid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ValueOperations<String,List<Article>>operations= redisTemplate.opsForValue();
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
        HashMap hashMap1=new HashMap();
        hashMap.put("userid",userid);
        List<Article> articles1=articleDao.getArticlelist(hashMap1);
        String key="articles_user_"+userid;
        if(redisTemplate.hasKey(key)){
            redisTemplate.delete(key);
        }
        if(articles1!=null){
            operations.set(key,articles1,5,TimeUnit.HOURS);
        }
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("update successfully!");
    }

    @Override
    public Result getonearticle(int userid, int id) {
        String key="article_"+String.valueOf(id)+"_user_"+userid;
        ValueOperations<String,Article> operations= redisTemplate.opsForValue();
        if(redisTemplate.hasKey(key)){
            Article article_= operations.get(key);
            return Result.succ(article_);
        }
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        List<Article> article=articleDao.getArticlebyid(hashMap);
        sqlSession.commit();
        sqlSession.close();
        if(article!=null){
            operations.set(key, article.get(0), 5, TimeUnit.HOURS);
        }
        return Result.succ(article.get(0));
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

    @Override
    public Result addoneview(String ipaddress,String userid, String id,String count) {
        String key=ipaddress+"_addview_user_"+userid+"_id_"+id;
        System.out.println(key);
        ValueOperations<String,String>operations= redisTemplate.opsForValue();
        if(redisTemplate.hasKey(key)){
            return Result.succ("");
        }
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        hashMap.put("count",count);
        articleDao.addview(hashMap);
        operations.set(key, "", 2, TimeUnit.MINUTES);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("add one view successfully!");
    }

    @Override
    public Result getarticlebylabel(int userid, String labelname) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("labelname",labelname);
        List<Article> article=articleDao.getArticlelistbylabel(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(article);
    }

    @Override
    public Result getarticlebyblock(int userid, String blockname) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("blockname",blockname);
        List<Article> article=articleDao.getArticlelistbyblock(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(article);
    }

    @Override
    public Result getarticlebysearch(int userid, String searchcontent) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ArticleDao articleDao=sqlSession.getMapper(ArticleDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("content","%"+searchcontent+"%");
        List<Article> article=articleDao.getArticlelistbysearch(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(article);
    }
}
