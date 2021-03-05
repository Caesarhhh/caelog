package com.ownblog.caelog.service.impl;

import com.ownblog.caelog.Dao.ChatDao;
import com.ownblog.caelog.Dao.LetterDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Chat;
import com.ownblog.caelog.pojo.Letter;
import com.ownblog.caelog.pojo.tools.Time;
import com.ownblog.caelog.service.ChatService;
import com.ownblog.caelog.utils.BatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class ChatServiceImpl implements ChatService {
    @Override
    public Result addchat(int person1id, int person2id) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ChatDao chatDao=sqlSession.getMapper(ChatDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("person1id",person1id);
        hashMap.put("person2id",person2id);
        List<Chat> chats=chatDao.getChat(hashMap);
        int count=chatDao.getChatcount();
        int id_=0;
        if(chats.size()>0){
            return Result.fail("exist!");
        }
        if(count!=0){
            id_=chatDao.getbigestChatid()+1;
        }
        Chat chat=new Chat(person1id,person2id,id_,0);
        chatDao.insertChat(chat);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(chat);
    }

    @Override
    public Result changeifview(int id, int ifview) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ChatDao chatDao=sqlSession.getMapper(ChatDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("id",id);
        hashMap.put("ifview",ifview);
        chatDao.setifview(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("change successfully!");
    }

    @Override
    public Result deletechat(int id) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ChatDao chatDao=sqlSession.getMapper(ChatDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("id",id);
        chatDao.deleteChatbyid(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("delete successfully!");
    }

    @Override
    public Result getchat(int person1id, int person2id) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ChatDao chatDao=sqlSession.getMapper(ChatDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("person1id",person1id);
        hashMap.put("person2id",person2id);
        List<Chat> chats=chatDao.getChat(hashMap);
        if(chats.size()==0){
            return Result.fail("none chat!");
        }
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(chats.get(0));
    }

    @Override
    public Result getchatbyoneid(int userid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ChatDao chatDao=sqlSession.getMapper(ChatDao.class);
        List<Chat> chats=chatDao.getChatlistbyoneid(userid);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(chats);
    }

    @Override
    public Result getbysort(int userid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        ChatDao chatDao=sqlSession.getMapper(ChatDao.class);
        LetterDao letterDao=sqlSession.getMapper(LetterDao.class);
        List<Chat> chats=chatDao.getChatlistbyoneid(userid);
        List<Time> times=new ArrayList<Time>();
        for(int i=0;i<chats.size();i++){
            int tempchatid=(chats.get(i)).getId();
            HashMap temphashmap=new HashMap();
            temphashmap.put("chatid",tempchatid);
            List<Letter>templetters=letterDao.getLetterlist(temphashmap);
            if(templetters.size()==0){
                times.add(new Time("2000-02-16 22:59:02.408"));
                continue;
            }
            Time temptime=new Time(templetters.get(0).getTime_());
            times.add(temptime);
        }
        for(int i=0;i<times.size()-1;i++){
            for(int j=0;j<times.size()-i-1;j++){
                if(times.get(j).compareTo(times.get(j+1))<0){
                    Time temptime=times.get(j);
                    times.set(j,times.get(j+1));
                    times.set(j+1,temptime);
                    Chat tempchat=chats.get(j);
                    chats.set(j,chats.get(j+1));
                    chats.set(j+1,tempchat);
                }
            }
        }
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(chats);
    }


}
