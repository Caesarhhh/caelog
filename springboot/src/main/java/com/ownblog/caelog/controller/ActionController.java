package com.ownblog.caelog.controller;

import com.ownblog.caelog.service.impl.ActionServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/action",method = RequestMethod.POST)
public class ActionController {
    @GetMapping(value = "/add")
    public Object addaction(@RequestParam("actorid")int actorid, @RequestParam("targetid")int targetid, @RequestParam("type_")String type_, @RequestParam("objectid")int objectid){
        ActionServiceImpl actionService=new ActionServiceImpl();
        return actionService.addAction(actorid,targetid,type_,objectid);
    }
    @GetMapping("/delete")
    public Object deleteaction(@RequestParam("actorid")int actorid, @RequestParam("targetid")int targetid, @RequestParam("type_")String type_, @RequestParam("objectid")int objectid){
        ActionServiceImpl actionService=new ActionServiceImpl();
        return actionService.deleteAction(actorid,targetid,type_,objectid);
    }
    @GetMapping("/getbyactor")
    public Object getaction(@RequestParam("actorid")int actorid){
        ActionServiceImpl actionService=new ActionServiceImpl();
        return actionService.getAction(actorid);
    }
    @GetMapping("/ifin")
    public Object ifin(@RequestParam("actorid")int actorid, @RequestParam("targetid")int targetid, @RequestParam("type_")String type_, @RequestParam("objectid")int objectid){
        ActionServiceImpl actionService=new ActionServiceImpl();
        return actionService.ifin(actorid,targetid,type_,objectid);
    }
}
