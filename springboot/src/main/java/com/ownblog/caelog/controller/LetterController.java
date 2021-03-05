package com.ownblog.caelog.controller;

import com.ownblog.caelog.service.impl.LetterServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/letter",method = RequestMethod.POST)
public class LetterController {
    @GetMapping(value = "/add")
    public Object addletter(@RequestParam("actorid")int actorid, @RequestParam("targetid")int targetid, @RequestParam("content")String content){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times=df.format(new Date());
        LetterServiceImpl letterService=new LetterServiceImpl();
        return letterService.addletter(actorid,targetid,content,times);
    }
    @GetMapping("/get")
    public Object getletters(@RequestParam("chatid")int chatid){
        LetterServiceImpl letterService=new LetterServiceImpl();
        return letterService.getletters(chatid);
    }
}
