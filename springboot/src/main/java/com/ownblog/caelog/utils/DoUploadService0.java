package com.ownblog.caelog.utils;
import com.ownblog.caelog.lang.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class DoUploadService0 {
    FtpProperties ftpProperties=new FtpProperties();
    public Result doUpload(MultipartFile multipartFile, int id){
        Result result=new Result();
        try {
            String oldFileName = multipartFile.getOriginalFilename();
            String newFileName = FileNameUtil.getFileName(id);
            String substring = oldFileName.substring(oldFileName.lastIndexOf("."));
            newFileName = newFileName + substring;
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            String str = format.format(date);
            String filePath = "/"+str;
            String remotepath="caelog/userdata/";
            remotepath+=String.valueOf(id);
            Boolean resultBoolean = FtpUtil.uploadFile(ftpProperties.getHost(), Integer.parseInt(ftpProperties.getPort()), ftpProperties.getUsername(), ftpProperties.getPassword(), ftpProperties.getBasePath(), remotepath, newFileName, multipartFile.getInputStream());
            if(resultBoolean==true){
                System.out.println("upload successfully!");
                return result.succ(remotepath+"/"+newFileName);
            }
            else{
                System.out.println("upload error!");
                return  result.fail("upload error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

    }

}
