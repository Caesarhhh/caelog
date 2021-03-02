package com.ownblog.caelog.controller;

import com.ownblog.caelog.service.impl.ChangeServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/change",method = RequestMethod.POST)
public class ChangeController {
    @GetMapping(value = "/add")
    public Object addchange(@RequestParam("userid")int userid, @RequestParam("content_")String content){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times=df.format(new Date());
        ChangeServiceImpl changeService=new ChangeServiceImpl();
        return changeService.addChange(userid,content,times);
    }
    @GetMapping("/delete")
    public Object deletechange(@RequestParam("userid")int userid,@RequestParam("id")int id){
        ChangeServiceImpl changeService=new ChangeServiceImpl();
        return changeService.deleteChange(userid,id);
    }
    @GetMapping("/get")
    public Object getchange(@RequestParam("userid")int userid){
        ChangeServiceImpl changeService=new ChangeServiceImpl();
        return changeService.getChange(userid);
    }
}
