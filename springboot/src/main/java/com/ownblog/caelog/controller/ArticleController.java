package com.ownblog.caelog.controller;

import com.ownblog.caelog.service.impl.ArticleServiceImpl;
import com.ownblog.caelog.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value="/article",method = RequestMethod.POST)
public class ArticleController {
    @Autowired
    ArticleServiceImpl articleService;
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addarticle(@RequestBody Map<String,Object> para){
        int userid= (int) para.get("userid");
        String content= (String) para.get("content");
        String title= (String) para.get("title");
        int blockid= (int) para.get("blockid");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String times=df.format(new Date());
        return articleService.addarticle(userid,title,content,times,blockid);
    }
    @GetMapping("/delete")
    public Object deletearticle(@RequestParam("userid")int userid,@RequestParam("id")int id){
        return articleService.deletearticle(userid,id);
    }
    @GetMapping("/update")
    public Object updatearticle(@RequestParam("userid")int userid,@RequestParam("id")int id,@RequestParam("title")String title,@RequestParam("content")String content,@RequestParam("blockid")int blockid){
        return articleService.updatearticle(userid,id,title,content,blockid);
    }
    @GetMapping("/get")
    public Object getarticle(@RequestParam("userid")int userid){
        return articleService.getarticle(userid);
    }
    @GetMapping("/getone")
    public Object getonearticle(@RequestParam("userid")int userid,@RequestParam("id")int id){
        return articleService.getonearticle(userid,id);
    }
    @GetMapping("/changegood")
    public Object addonegood(@RequestParam("userid")int userid,@RequestParam("id")int id,@RequestParam("count")int count){
        return articleService.addonegood(userid,id,count);
    }
    @RequestMapping(value="/addview",method = RequestMethod.POST)
    public Object addview(HttpServletRequest request, @RequestBody Map<String,Object> para){
        String userid= (String) para.get("userid");
        String id= (String) para.get("id");
        String count= String.valueOf((Integer) para.get("count"));
        return articleService.addoneview(IpUtil.getIpAddr(request),userid, id, count);
    }
    @GetMapping("/getbylabel")
    public Object getarticlebylabel(@RequestParam("userid")int userid,@RequestParam("labelname")String labelname){
        return articleService.getarticlebylabel(userid,labelname);
    }
    @GetMapping("/getbyblock")
    public Object getarticlebyblock(@RequestParam("userid")int userid,@RequestParam("blockname")String blockname){
        return articleService.getarticlebyblock(userid,blockname);
    }
    @GetMapping("/getbysearch")
    public Object getarticle(@RequestParam("userid")int userid,@RequestParam("searchcontent")String searchcontent){
        return articleService.getarticlebysearch(userid,searchcontent);
    }
}
