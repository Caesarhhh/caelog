package com.ownblog.caelog.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.ownblog.caelog.utils.ossUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class ossService{

    @Resource
    private ossUtil ossutil;

    public String uploadFileAvatar(MultipartFile file,int userid) throws Exception {
        //获取oss上传配置文件中的参数
        String bucketName = ossutil.getBucketname();
        String endpoint = ossutil.getEndpoint();
        String keyId = ossutil.getKeyid();
        String keySecret = ossutil.getKeysecret();

        OSS ossClient;
        InputStream inputStream;
        try {
            // 创建OSSClient实例。
            ossClient  = new OSSClientBuilder().build(endpoint, keyId, keySecret);
            // 上传文件流
            inputStream = file.getInputStream();

            //为了使得文件可以重复上传，每次上传的时候需要将文件名进行修改
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String newFileName = uuid + fileName;

            //获取当前日期,然后以日期和新的文件名组成全路径，使得oss中的文件按照日期进行分类存储
            String date = new DateTime().toString("yyyy/MM/dd");
            String fullFileName = "userdata/"+userid+"/"+date + "/" + newFileName;

            //第一个参数Bucket名称 第二个参数 上传到oss文件路径和文件名称
            ossClient.putObject(bucketName, fullFileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();
            return "https://"+bucketName+"."+ endpoint+"/"+fullFileName;
        } catch (Exception e) {
            throw new Exception("error");
        }
    }
}
