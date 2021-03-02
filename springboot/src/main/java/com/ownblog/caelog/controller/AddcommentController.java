package com.ownblog.caelog.controller;

import com.ownblog.caelog.service.impl.AddcommentServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value="/addcomment",method = RequestMethod.POST)
public class AddcommentController {
    @GetMapping(value = "/add")
    public Object addaddcomment(@RequestParam("userid")int userid, @RequestParam("commentid")int commentid, @RequestParam("content_")String content, @RequestParam("articleid")int articleid, @RequestParam("actorid")int actorid,@RequestParam("name_")String name_){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times=df.format(new Date());
        AddcommentServiceImpl addcommentService=new AddcommentServiceImpl();
        return addcommentService.addAddcomment(userid,articleid,actorid,commentid,content,times,name_);
    }
    @GetMapping("/delete")
    public Object deleteaddcomment(@RequestParam("userid")int userid,@RequestParam("id")int id,@RequestParam("articleid")int articleid){
        AddcommentServiceImpl addcommentService=new AddcommentServiceImpl();
        return addcommentService.deleteAddcomment(userid,id,articleid);
    }
    @GetMapping("/get")
    public Object getaddcomment(@RequestParam("userid")int userid,@RequestParam("articleid")int articleid,@RequestParam("commentid")int commentid){
        AddcommentServiceImpl addcommentService=new AddcommentServiceImpl();
        return addcommentService.getAddcomments(userid,articleid,commentid);
    }
    @GetMapping("/changegoodnum")
    public Object deleteaddcomment(@RequestParam("userid")int userid,@RequestParam("id")int id,@RequestParam("articleid")int articleid,@RequestParam("count")int count){
        AddcommentServiceImpl addcommentService=new AddcommentServiceImpl();
        return addcommentService.changegoodnum(userid,id,articleid,count);
    }
}
