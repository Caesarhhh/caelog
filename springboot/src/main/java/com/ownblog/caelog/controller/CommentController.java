package com.ownblog.caelog.controller;

import com.ownblog.caelog.service.impl.ChangeServiceImpl;
import com.ownblog.caelog.service.impl.CommentServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value="/comment",method = RequestMethod.POST)
public class CommentController {
    @GetMapping(value = "/add")
    public Object addcomment(@RequestParam("userid")int userid, @RequestParam("content_")String content,@RequestParam("articleid")int articleid,@RequestParam("actorid")int actorid){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String times=df.format(new Date());
        CommentServiceImpl commentService=new CommentServiceImpl();
        return commentService.addComment(userid,articleid,actorid,content,times);
    }
    @GetMapping("/delete")
    public Object deletecomment(@RequestParam("userid")int userid,@RequestParam("id")int id,@RequestParam("articleid")int articleid){
        CommentServiceImpl commentService=new CommentServiceImpl();
        return commentService.deleteComment(userid,id,articleid);
    }
    @GetMapping("/get")
    public Object getcomment(@RequestParam("userid")int userid,@RequestParam("articleid")int articleid){
        CommentServiceImpl commentService=new CommentServiceImpl();
        return commentService.getComments(userid,articleid);
    }
    @GetMapping("/changegood")
    public Object changegood(@RequestParam("userid")int userid,@RequestParam("id")int id,@RequestParam("articleid")int articleid,@RequestParam("count")int count){
        CommentServiceImpl commentService=new CommentServiceImpl();
        return commentService.changegoodnum(userid,id,articleid,count);
    }
}
