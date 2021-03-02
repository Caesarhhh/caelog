package com.ownblog.caelog.controller;

import com.ownblog.caelog.service.impl.BlocksServiceImpl;
import com.ownblog.caelog.service.impl.LabelsServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/labels",method = RequestMethod.POST)
public class LabelsController {
    @GetMapping(value = "/add")
    public Object addlabel(@RequestParam("userid")int userid,@RequestParam("name_")String name_,@RequestParam("articleid")int articleid){
        LabelsServiceImpl labelsService=new LabelsServiceImpl();
        return labelsService.addlabels(userid,name_,articleid);
    }
    @GetMapping("/delete")
    public Object deletelabel(@RequestParam("userid")int userid,@RequestParam("id")int id){
        LabelsServiceImpl labelsService=new LabelsServiceImpl();
        return labelsService.deletelabels(userid,id);
    }
    @GetMapping("/get")
    public Object getlabels(@RequestParam("userid")int userid){
        LabelsServiceImpl labelsService=new LabelsServiceImpl();
        return labelsService.getlabels(userid);
    }
    @GetMapping("/getar")
    public Object getlabelsar(@RequestParam("userid")int userid,@RequestParam("articleid")int articleid){
        LabelsServiceImpl labelsService=new LabelsServiceImpl();
        return labelsService.getlabelsar(userid,articleid);
    }
}
