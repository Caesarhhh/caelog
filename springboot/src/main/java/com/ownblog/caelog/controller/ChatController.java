package com.ownblog.caelog.controller;

import com.ownblog.caelog.service.impl.ChatServiceImpl;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/chat",method = RequestMethod.POST)
public class ChatController {
    @GetMapping(value = "/add")
    public Object addchat(@RequestParam("person1id")int person1id,@RequestParam("person2id")int person2id){
        ChatServiceImpl chatService=new ChatServiceImpl();
        return chatService.addchat(person1id,person2id);
    }
    @GetMapping("/changeifview")
    public Object changeifview(@RequestParam("id")int id,@RequestParam("ifview")int ifview){
        ChatServiceImpl chatService=new ChatServiceImpl();
        return chatService.changeifview(id,ifview);
    }
    @GetMapping("/get")
    public Object getchat(@RequestParam("person1id")int person1id,@RequestParam("person2id")int person2id){
        ChatServiceImpl chatService=new ChatServiceImpl();
        return chatService.getchat(person1id,person2id);
    }
    @GetMapping("/getbyoneid")
    public Object getchat(@RequestParam("person1id")int userid){
        ChatServiceImpl chatService=new ChatServiceImpl();
        return chatService.getchatbyoneid(userid);
    }
    @GetMapping("/getbysort")
    public Object getbysort(@RequestParam("userid")int userid){
        ChatServiceImpl chatService=new ChatServiceImpl();
        return chatService.getbysort(userid);
    }
}
