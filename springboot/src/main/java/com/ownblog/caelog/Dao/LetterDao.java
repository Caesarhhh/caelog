package com.ownblog.caelog.Dao;


import com.ownblog.caelog.pojo.Letter;

import java.util.HashMap;
import java.util.List;

public interface LetterDao {
    List<Letter> getLetterlist(HashMap hashMap);
    void insertLetter(Letter letter);
    void deleteLetterbychatid(HashMap hashMap);
}
