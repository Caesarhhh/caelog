package com.ownblog.caelog.controller;

import com.ownblog.caelog.pojo.tools.Time;
import com.ownblog.caelog.service.impl.AnnouncementServiceImpl;
import com.ownblog.caelog.service.impl.SocialhrefServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value="/announcement",method = RequestMethod.POST)
public class AnnouncementController {
    @GetMapping(value = "/add")
    public Object addannouncement(@RequestParam("userid")int userid, @RequestParam("content")String content, @RequestParam("title")String title){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String times=df.format(new Date());
        AnnouncementServiceImpl announcementService=new AnnouncementServiceImpl();
        return announcementService.addannouncement(userid,times,content,title);
    }
    @GetMapping("/delete")
    public Object deleteannouncement(@RequestParam("userid")int userid,@RequestParam("id")int hrefid){
        AnnouncementServiceImpl announcementService=new AnnouncementServiceImpl();
        return announcementService.deletedannouncement(userid,hrefid);
    }
    @GetMapping("/changeindex")
    public Object changeindex(@RequestParam("userid")int userid,@RequestParam("index1")int index1,@RequestParam("index2")int index2){
        AnnouncementServiceImpl announcementService=new AnnouncementServiceImpl();
        return announcementService.changeindex(userid,index1,index2);
    }
    @GetMapping("/get")
    public Object getannouncements(@RequestParam("userid")int userid,@RequestParam("randomcode")int randomcode){
        AnnouncementServiceImpl announcementService=new AnnouncementServiceImpl();
        return announcementService.getdannouncement(userid);
    }
}
