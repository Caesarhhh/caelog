package com.ownblog.caelog.controller;

import com.ownblog.caelog.utils.DoUploadService0;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/files",method = RequestMethod.POST)
public class UploadController {
    @GetMapping("/upload")
    public Object uploadfile(@RequestParam("file")MultipartFile multipartFile,@RequestParam("userid")int userid){
        DoUploadService0 doUploadService0=new DoUploadService0();
        return doUploadService0.doUpload(multipartFile,userid);
    }
}
