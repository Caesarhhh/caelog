package com.ownblog.caelog.service.impl;

import com.ownblog.caelog.Dao.ChatDao;
import com.ownblog.caelog.Dao.LetterDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Chat;
import com.ownblog.caelog.pojo.Letter;
import com.ownblog.caelog.service.LetterService;
import com.ownblog.caelog.utils.BatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LetterServiceImpl implements LetterService {

    @Override
    public Result addletter(int actorid, int targetid, String content, String time) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        LetterDao letterDao=sqlSession.getMapper(LetterDao.class);
        ChatDao chatDao=sqlSession.getMapper(ChatDao.class);
        HashMap hashMap1=new HashMap();
        hashMap1.put("person1id",actorid);
        hashMap1.put("person2id",targetid);
        Chat chat=chatDao.getChat(hashMap1).get(0);
        HashMap hashMap=new HashMap();
        hashMap.put("id",chat.getId());
        hashMap.put("ifview",0);
        chatDao.setifview(hashMap);
        int chatid=chat.getId();
        Letter letter=new Letter(chatid,time,actorid,targetid,content);
        letterDao.insertLetter(letter);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(letter);
    }

    @Override
    public Result deleteletter(int chatid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        LetterDao letterDao=sqlSession.getMapper(LetterDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("chatid",chatid);
        letterDao.deleteLetterbychatid(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("delete successfully!");
    }

    @Override
    public Result getletters(int chatid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        LetterDao letterDao=sqlSession.getMapper(LetterDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("chatid",chatid);
        List<Letter>letters=letterDao.getLetterlist(hashMap);
        Collections.sort(letters);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(letters);
    }
}
