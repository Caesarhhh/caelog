package com.ownblog.caelog.controller;

import com.ownblog.caelog.service.impl.SocialhrefServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/socialhref",method = RequestMethod.POST)
public class SocialhrefController {
    @GetMapping(value = "/add")
    public Object addhref(@RequestParam("userid")int userid,@RequestParam("imgsrc")String imgsrc,@RequestParam("href")String href,@RequestParam("comment")String comment){
        SocialhrefServiceImpl socialhrefimpl=new SocialhrefServiceImpl();
        return socialhrefimpl.addhref(userid,imgsrc,href,comment);
    }
    @GetMapping("/delete")
    public Object deletehref(@RequestParam("userid")int userid,@RequestParam("hrefid")int hrefid){
        SocialhrefServiceImpl socialhrefimpl=new SocialhrefServiceImpl();
        return socialhrefimpl.deletehref(userid,hrefid);
    }
    @GetMapping("/changevisable")
    public Object changevisable(@RequestParam("userid")int userid,@RequestParam("hrefid")int hrefid,@RequestParam("ifuse")int ifuse){
        SocialhrefServiceImpl socialhrefimpl=new SocialhrefServiceImpl();
        return socialhrefimpl.changevisble(userid,hrefid,ifuse);
    }
    @GetMapping("/get")
    public Object gethrefs(@RequestParam("userid")int userid){
        SocialhrefServiceImpl socialhrefimpl=new SocialhrefServiceImpl();
        return socialhrefimpl.gethrefsbyid(userid);
    }
}
