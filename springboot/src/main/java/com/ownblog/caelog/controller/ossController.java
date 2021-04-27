package com.ownblog.caelog.controller;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.service.ossService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;

@Api("文件上传")
@RestController
@RequestMapping("/files")
@CrossOrigin
public class ossController {
    @Resource
    private ossService ossservice;

    //上传头像的方法
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public Result UploadOssFile(@RequestParam("file") MultipartFile file,@RequestParam("userid")int userid) throws Exception {
        String url = ossservice.uploadFileAvatar(file,userid);
        HashMap<String, String> map = new HashMap<>();
        map.put("url",url);
        return Result.succ(map);
    }
    @PostMapping("/uploadtemp")
    @ApiOperation(value = "文件上传")
    public Result UploadOssFiletemp(@RequestParam("file") MultipartFile file) throws Exception {
        int userid=-1;
        String url = ossservice.uploadFileAvatar(file,userid);
        HashMap<String, String> map = new HashMap<>();
        map.put("url",url);
        return Result.succ(map);
    }
}
