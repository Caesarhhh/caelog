package com.ownblog.caelog.utils;
import com.ownblog.caelog.lang.Result;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class sendEmailUtil {
    public static Result send(String address, String title, String content)throws MessagingException, GeneralSecurityException{
        Properties properties = new Properties();
        properties.setProperty("mail.host","smtp.163.com");
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.auth","true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("Caesar216@163.com","UPEKFRIZZMEUGOKH");
            }
        });
        session.setDebug(true);
        Transport transport = session.getTransport();
        transport.connect("smtp.163.com","Caesar216@163.com","UPEKFRIZZMEUGOKH");
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress("Caesar216@163.com"));
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(address));
        mimeMessage.setSubject(title);
        mimeMessage.setContent(content,"text/html;charset=UTF-8");
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        transport.close();
        return Result.succ("send successfully!");
    }
    public static void main(String[] args)throws MessagingException, GeneralSecurityException  {
        sendEmailUtil.send("yujkl5487@qq.com","test","test");
    }
}
