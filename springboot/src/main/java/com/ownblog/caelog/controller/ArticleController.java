package com.ownblog.caelog.controller;

import com.ownblog.caelog.service.impl.ArticleServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping(value="/article",method = RequestMethod.POST)
public class ArticleController {
    @GetMapping(value = "/add")
    public Object addarticle(@RequestParam("userid")int userid, @RequestParam("content")String content, @RequestParam("title")String title, @RequestParam("blockid")int blockid){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String times=df.format(new Date());
        ArticleServiceImpl articleService=new ArticleServiceImpl();
        return articleService.addarticle(userid,title,content,times,blockid);
    }
    @GetMapping("/delete")
    public Object deletearticle(@RequestParam("userid")int userid,@RequestParam("id")int id){
        ArticleServiceImpl articleService=new ArticleServiceImpl();
        return articleService.deletearticle(userid,id);
    }
    @GetMapping("/update")
    public Object updatearticle(@RequestParam("userid")int userid,@RequestParam("id")int id,@RequestParam("title")String title,@RequestParam("content")String content,@RequestParam("blockid")int blockid){
        ArticleServiceImpl articleService=new ArticleServiceImpl();
        return articleService.updatearticle(userid,id,title,content,blockid);
    }
    @GetMapping("/get")
    public Object getarticle(@RequestParam("userid")int userid){
        ArticleServiceImpl articleService=new ArticleServiceImpl();
        return articleService.getarticle(userid);
    }
    @GetMapping("/getone")
    public Object getonearticle(@RequestParam("userid")int userid,@RequestParam("id")int id){
        ArticleServiceImpl articleService=new ArticleServiceImpl();
        return articleService.getonearticle(userid,id);
    }
    @GetMapping("/changegood")
    public Object addonegood(@RequestParam("userid")int userid,@RequestParam("id")int id,@RequestParam("count")int count){
        ArticleServiceImpl articleService=new ArticleServiceImpl();
        return articleService.addonegood(userid,id,count);
    }
    @GetMapping("/getbylabel")
    public Object getarticlebylabel(@RequestParam("userid")int userid,@RequestParam("labelname")String labelname){
        ArticleServiceImpl articleService=new ArticleServiceImpl();
        return articleService.getarticlebylabel(userid,labelname);
    }
    @GetMapping("/getbyblock")
    public Object getarticlebyblock(@RequestParam("userid")int userid,@RequestParam("blockname")String blockname){
        ArticleServiceImpl articleService=new ArticleServiceImpl();
        return articleService.getarticlebyblock(userid,blockname);
    }
    @GetMapping("/getbysearch")
    public Object getarticle(@RequestParam("userid")int userid,@RequestParam("searchcontent")String searchcontent){
        ArticleServiceImpl articleService=new ArticleServiceImpl();
        return articleService.getarticlebysearch(userid,searchcontent);
    }
}
