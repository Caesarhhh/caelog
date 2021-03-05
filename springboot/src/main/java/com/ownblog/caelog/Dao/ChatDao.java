package com.ownblog.caelog.Dao;


import com.ownblog.caelog.pojo.Chat;

import java.util.HashMap;
import java.util.List;

public interface ChatDao {
    int getbigestChatid();
    List<Chat> getChat(HashMap hashMap);
    void insertChat(Chat chat);
    void deleteChatbyid(HashMap hashMap);
    void setifview(HashMap hashMap);
    List<Chat> getChatlistbyoneid(int userid);
    int getChatcount();
}
