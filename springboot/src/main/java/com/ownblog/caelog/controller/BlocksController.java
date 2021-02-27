package com.ownblog.caelog.controller;

import com.ownblog.caelog.service.impl.BlocksServiceImpl;
import com.ownblog.caelog.service.impl.SocialhrefServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/blocks",method = RequestMethod.POST)
public class BlocksController {
    @GetMapping(value = "/add")
    public Object addhref(@RequestParam("userid")int userid, @RequestParam("imgsrc")String imgsrc,@RequestParam("name_")String name_, @RequestParam("remark")String remark){
        BlocksServiceImpl blocksService=new BlocksServiceImpl();
        return blocksService.addblocks(userid,imgsrc,name_,remark);
    }
    @GetMapping("/delete")
    public Object deletehref(@RequestParam("userid")int userid,@RequestParam("id")int id){
        BlocksServiceImpl blocksService=new BlocksServiceImpl();
        return blocksService.deleteblocks(userid,id);
    }
    @GetMapping("/get")
    public Object gethrefs(@RequestParam("userid")int userid){
        BlocksServiceImpl blocksService=new BlocksServiceImpl();
        return blocksService.getblocks(userid);
    }
}
