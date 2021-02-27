package com.ownblog.caelog.utils;
import java.util.Random;
public class FileNameUtil {
    public static String getFileName(Integer userId){
        long currentTimeMillis = System.currentTimeMillis();
        Random random=new Random();
        int randomNum = random.nextInt(999);
        String fileName = currentTimeMillis + userId + String.format("%03d", randomNum);
        return fileName;
    }

    public static void main(String[] args) {
        String fileName = FileNameUtil.getFileName(1);
        System.out.println(fileName);
    }

}
